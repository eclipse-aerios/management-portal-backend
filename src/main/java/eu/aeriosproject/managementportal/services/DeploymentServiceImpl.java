package eu.aeriosproject.managementportal.services;

import eu.aeriosproject.managementportal.models.NetworkPort;
import eu.aeriosproject.managementportal.models.Service;
import eu.aeriosproject.managementportal.models.ServiceComponent;
import eu.aeriosproject.managementportal.models.ServiceComponentEntity;
import eu.aeriosproject.managementportal.utils.NgsildConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class DeploymentServiceImpl implements DeploymentService{
    Logger logger = LoggerFactory.getLogger(DeploymentServiceImpl.class);
    private final String NGSILD_PREFIX = NgsildConverter.SERVICE;
    @Value("${aerios.orion-ld}")
    private String ORION_URL;
    @Value("${aerios.hlo-fe}")
    private String HLO_FE_URL;
    @Value("${aerios.entrypoint-balancer.url}")
    private String EP_BALANCER_URL;
    @Value("${aerios.entrypoint-balancer.enabled}")
    private boolean USE_EP_BALANCER;

    @Autowired
    @Qualifier("hloRestTemplate")
    private RestTemplate hloClient;

    @Autowired
    @Qualifier("orionRestTemplate")
    private RestTemplate orionClient;

    @Override
    public List<Service> listServices() {
        ResponseEntity<Service[]> response = orionClient
                .getForEntity(ORION_URL+"?type=Service&format=simplified", Service[].class);
        return Arrays.asList(response.getBody());
    }

    @Override
    public Service getServiceById(String serviceId) {
        try {
            ResponseEntity<Service> response = orionClient
                    .getForEntity(ORION_URL+"/{serviceId}?type=Service&format=simplified", Service.class, serviceId);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found");
        }
    }

    @Override
    public List<ServiceComponentEntity> listServiceComponents(String id) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(ORION_URL)
                .queryParam("type", "ServiceComponent")
                .queryParam("format", "simplified")
                .queryParam("q",      "service==\"{serviceId}\"")
                .buildAndExpand(Collections.singletonMap("serviceId", id))
                .encode()
                .toUri();

        ResponseEntity<List<ServiceComponentEntity>> response =
                orionClient.exchange(
                        uri,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );
        List<ServiceComponentEntity> entities = Optional.ofNullable(response.getBody()).orElseGet(Collections::emptyList);
        logger.debug("{} components found for this service", entities.size());
        return entities;
    }

    @Override
    public List<ServiceComponent> listServiceComponentsWithPorts(String id) {
        List<ServiceComponentEntity> entities = listServiceComponents(id);
        logger.debug("{} components found for this service", entities.size());
        return entities.stream().map(this::entityToServiceComponent).toList();
    }

    private ServiceComponent entityToServiceComponent(ServiceComponentEntity entity) {
        ServiceComponent component = new ServiceComponent();
        ArrayList<NetworkPort> ports;
        BeanUtils.copyProperties(entity, component, "networkPorts");
        logger.debug("{} component", entity.getId());

        if (entity.getNetworkPorts() != null) {
            ports = entity.getNetworkPorts()
                    .stream()
                    .map(this::getNetworkPortById)
                    .peek(port -> port.setExposed(entity.getExposePorts()))
                    .collect(Collectors.toCollection(ArrayList::new));
        } else ports = new ArrayList<NetworkPort>();
        logger.debug("{} ports found for this component", ports.size());
        component.setNetworkPorts(ports);
        return component;
    }

    @Override
    public List<NetworkPort> getNetworkPortDetailsFromComponent(String componentId) {
        ResponseEntity<ServiceComponentEntity> response = orionClient
                .getForEntity(ORION_URL+"/{componentId}?format=simplified", ServiceComponentEntity.class, componentId);
        ServiceComponentEntity componentEntity = response.getBody();
        if(componentEntity == null) return Collections.emptyList();

        if (componentEntity.getNetworkPorts() == null) return Collections.emptyList();
        return componentEntity.getNetworkPorts()
                .stream()
                .map(this::getNetworkPortById)          // pure mapping
                .peek(port -> port.setExposed(
                        componentEntity.getExposePorts()))
                .toList();
    }

    private NetworkPort getNetworkPortById(String portId) {
        ResponseEntity<NetworkPort> response = orionClient
                .getForEntity(ORION_URL+"/{portId}?format=simplified", NetworkPort.class, portId);
        return response.getBody();
    }

    @Override
    public void deleteServiceById(String serviceId) {
        logger.debug("Sending deletion of Service {} to HLO FE... ", serviceId);
        hloClient.delete(HLO_FE_URL+"/hlo_fe/services/".concat(serviceId));
    }

    @Override
    public void clearServiceById(String serviceId) {
        logger.debug("Deleting permanently the Service {}. Deleting the related entities from Orion-LD...", serviceId);
        List<ServiceComponentEntity> components = listServiceComponents(serviceId);
        orionClient.delete(ORION_URL+"/{serviceId}", serviceId);

        components.forEach(c-> {
            // Delete ServiceComponent entitiy
            orionClient.delete(ORION_URL+"/{entityId}", c.getId());
            // Delete NetworkPort entities
            if (c.getNetworkPorts() != null)
                c.getNetworkPorts().forEach(p->orionClient.delete(ORION_URL+"/{entityId}", p));
            // Delete InfrastructureElementRequirements entities
            orionClient.delete(ORION_URL+"/{entityId}", c.getInfrastructureElementRequirements());
        });

    }

    @Override
    public void initOrchestration(String tosca, String serviceId) {
        // TODO also send serviceName or include in TOSCA
        serviceId = NGSILD_PREFIX.concat(serviceId);
        logger.debug("Service ID in NGSI-LD format: {}", serviceId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/yaml"));
//        headers.setContentType(MediaType.valueOf("application/x-yaml"));
        HttpEntity<String> request = new HttpEntity<>(tosca, headers);
        // TODO improve error handling
        if (USE_EP_BALANCER) {
            logger.info("Sending TOSCA to the Entrypoint Balancer to distribute the request among the Domains of the continuum...");
            String epResponse = hloClient.postForObject(EP_BALANCER_URL + "/entrypoint-balancer/distribute/" + serviceId, request, String.class);
            logger.debug(epResponse);
        } else {
            logger.info("Sending TOSCA to the configured HLO-FE...");
            hloClient.postForObject(HLO_FE_URL + "/hlo_fe/services/" + serviceId, request, Void.class);
        }
    }

    @Override
    public void reOrchestrate(String serviceId){
        // Service ID in NGSI-LD format
        logger.debug("Reorchestrating Service ID in NGSI-LD format: {}", serviceId);
        if (USE_EP_BALANCER) {
            logger.info("Sending TOSCA to the Entrypoint Balancer to distribute the request among the Domains of the continuum...");
            hloClient.put(EP_BALANCER_URL + "/entrypoint-balancer/distribute/" + serviceId, null);
        } else {
            logger.info("Sending TOSCA to the configured HLO-FE...");
            hloClient.put(HLO_FE_URL+"/hlo_fe/services/"+serviceId, null);
        }
    }
}
