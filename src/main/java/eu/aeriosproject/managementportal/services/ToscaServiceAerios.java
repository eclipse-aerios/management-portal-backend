package eu.aeriosproject.managementportal.services;

import eu.aeriosproject.managementportal.models.serviceform.Argument;
import eu.aeriosproject.managementportal.models.serviceform.Components;
import eu.aeriosproject.managementportal.models.serviceform.NewDeployment;
import eu.aeriosproject.managementportal.models.tosca.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Service
public class ToscaServiceAerios implements ToscaService{
    Logger logger = LoggerFactory.getLogger(ToscaServiceAerios.class);
    private final String TOSCA_VERSION = "tosca_simple_yaml_1_3";
    @Value("${aerios.hlo-fe}")
    private String HLO_FE_URL;

    @Override
    public Tosca buildTosca(NewDeployment newDeployment) {
        Tosca tosca = new Tosca();
        tosca.setDescription(newDeployment.getConfig().getDescription());
        tosca.setTosca_definitions_version(TOSCA_VERSION);
        tosca.setServiceOverlay(newDeployment.getConfig().isServiceOverlay());
        Map<String, ServiceComponent> nodeTemplates = new HashMap<>();
        logger.debug("Received a Service composed of {} components", newDeployment.getServiceComponents().size());

        for (Components sc:newDeployment.getServiceComponents()){
            logger.debug("Generating Service component \"{}\"", sc.getName());
            // Create Node_templates
            Artifacts artifacts = createArtifacts(sc);
            Interfaces interfaces = createInterfaces(sc);
            OrchestrationMode orchestrationMode = OrchestrationMode.valueOf(sc.getType().toUpperCase());
            List<Map<String, Requirement>> requirements = createRequirements(orchestrationMode, sc);

            ServiceComponent nodeTemplate = createNodeTemplate(artifacts, interfaces, requirements, sc.getDeployAsJob());
            nodeTemplates.put(sc.getName(), nodeTemplate);
        }
        tosca.setNode_templates(nodeTemplates);
        return tosca;
    }

    @Override
    public ServiceComponent createNodeTemplate(Artifacts artifacts, Interfaces interfaces, List<Map<String, Requirement>> requirements, boolean isJob) {
        return new ServiceComponent(artifacts, interfaces, requirements, isJob);
    }

    @Override
    public Interfaces createInterfaces(Components component) {
        logger.debug("Generating interfaces: CLI arguments and environment variables");
        Interfaces interfaces = new Interfaces();
        Standard standard = new Standard();
        interfaces.setStandard(standard);
        Create create = new Create();
        Inputs inputs = new Inputs();
        List<Map<String, String>> cliArgs = new ArrayList<Map<String, String>>();
        List<Map<String, String>> envVars = new ArrayList<Map<String, String>>();
        standard.setCreate(create);
        create.setInputs(inputs);

        for(Argument arg:component.getArguments()){
            HashMap<String, String> input = new HashMap<String, String>(){{
                put(arg.getKey(), (arg.getValue()==null) ? "" : arg.getValue());
            }};
            if (arg.getType().equals("CLI")) cliArgs.add(input);
            else if (arg.getType().equals("ENV")) envVars.add(input);
        }

        inputs.setCliArgs(cliArgs);
        inputs.setEnvVars(envVars);
        return interfaces;
    }

    @Override
    public Artifacts createArtifacts(Components component) {
        logger.debug("Generating artifacts: container image");
        if (component.getContainerImage().isBlank() || component.getContainerImage().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty container registry");

        ApplicationImage image = new ApplicationImage(component.getIsPrivateImage());
        String imageName = component.getContainerImage().split(":")[0].replaceAll("\\s+", "");
        String imageTag;

        if(checkDockerHub(imageName)) {
            logger.debug("Dockerhub image");
            try {
                imageTag = component.getContainerImage().split(":")[1].replaceAll("\\s+", "");
            } catch (ArrayIndexOutOfBoundsException e) {
                logger.debug("No tag present in image, so setting it to latest...");
                imageTag = "latest";
            }
            logger.debug("Image name: " + imageName);
            logger.debug("Image Tag: "+imageTag);
            image.setFile(imageName + ":" + imageTag);
            image.setRepository("docker_hub");
        } else {
            logger.debug("Image not from Dockerhub. Setting custom file and repository...");
            String imageRepository;
            try {
                URI uri = new URI("http://"+component.getContainerImage());
                if(uri.getPort()==-1) imageRepository=uri.getHost();
                else imageRepository=uri.getHost()+":"+uri.getPort();

                imageName = uri.getPath().replaceFirst("/","");
                if(imageName.split(":").length==1) imageTag = "latest";
                else {
                    imageTag = imageName.split(":")[1];
                    imageName = imageName.split(":")[0];
                }
            } catch (URISyntaxException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The container registry is not valid");
            }
            logger.debug("Image repository: " + imageRepository);
            logger.debug("Image name: " + imageName);
            logger.debug("Image tag: " + imageTag);
            image.setFile(imageName + ":" + imageTag);
            image.setRepository(imageRepository);
        }

        if(component.getIsPrivateImage()){
            image.setUsername(component.getPrivateImage().getUserName());
            image.setPassword(component.getPrivateImage().getUserPassword());
        }

        Artifacts artifact = new Artifacts();
        artifact.setApplication_image(image);
        return artifact;
    }

    @Override
    public List<Map<String, Requirement>> createRequirements(OrchestrationMode mode, Components component) {
        List<Map<String, Requirement>> requirements = new ArrayList<>();
        logger.debug("Creating network requirements");
        if (component.getNetworkPorts()!=null && !component.getNetworkPorts().isEmpty()) {
            requirements.add(new HashMap<String, Requirement>() {{
                put("network", createNetworkRequirements(component.getNetworkPorts(), component.getExposePorts()));
            }});
        }
        logger.debug("Creating host requirements");
        requirements.add(new HashMap<String, Requirement>(){{
            put("host", createHostRequirements(mode, component));
        }});
        return requirements;
    }

    public NetworkRequirement createNetworkRequirements(List<eu.aeriosproject.managementportal.models.serviceform.NetworkPort> ports, Boolean exposePorts) {
        NetworkRequirement nr = new NetworkRequirement();
        NetworkProperties np = new NetworkProperties();

        HashMap<String, eu.aeriosproject.managementportal.models.tosca.NetworkPort> toscaPorts = new HashMap<String, eu.aeriosproject.managementportal.models.tosca.NetworkPort>();
        for (int i=0; i< ports.size(); i++) {
            PortProperties pp = new PortProperties(
                    Collections.singletonList(ports.get(i).getProtocol().toLowerCase()),
                    ports.get(i).getPortNumber()
            );
            eu.aeriosproject.managementportal.models.tosca.NetworkPort networkPort = new eu.aeriosproject.managementportal.models.tosca.NetworkPort(pp);
            toscaPorts.put("port"+(i+1), networkPort);
        }
        nr.setProperties(np);
        np.setPorts(toscaPorts);
        np.setExposePorts(exposePorts);

        return nr;
    }

    public HostRequirement createHostRequirements(OrchestrationMode mode, Components component){
        NodeFilter nodeFilter = new NodeFilter();
        if(mode.equals(OrchestrationMode.MANUAL)) {
            logger.debug("Manual orchestration. Setting selectedIE: {}", component.getSelectedIe());
            NodeProperties selectedIE = new NodeProperties(Collections.singletonList(component.getSelectedIe()));
            nodeFilter.setProperties(selectedIE);
        } else if (mode.equals(OrchestrationMode.SEMIAUTO)){
            logger.debug("Semi-automatic orchestration. Generating nodeCapabilities...");
            NodeProperties preSelectedIEs = new NodeProperties(component.getIePool());
            nodeFilter.setProperties(preSelectedIEs);
        } else if (mode.equals(OrchestrationMode.AUTO)){
            logger.debug("Automatic orchestration. Generating nodeCapabilities...");
            List<Map<String, Object>> nodeCapabilities = new ArrayList<>();

            // HOST properties
            HostProperties hostProperties = new HostProperties();
            // generate host-> properties
            Map<String,Map<String,Object>> properties = new HashMap<String,Map<String,Object>>();

            // TODO change Map to Map<String,String> if we're using String for all properties
            if(component.getCpu()!=null){
                Map<String,Object> cpuUsage = new HashMap<String,Object>(){{
                    Float cpu = component.getCpu()/100;
                    put(Comparisons.LESS_OR_EQUAL.getToscaName(), cpu.toString());
                }};
                properties.put("cpu_usage", cpuUsage);
            }
            if(component.getCpuArch()!=null){
                Map<String,Object> cpuArch = new HashMap<String,Object>(){{
                    put(Comparisons.EQUAL.getToscaName(), component.getCpuArch().toLowerCase());
                }};
                properties.put("cpu_arch", cpuArch);
            }
            if(component.getRam()!=null){
                Map<String,Object> memSize = new HashMap<String,Object>(){{
                    Float ramInMb = component.getRam()*1000;
                    Integer integerValue = Math.round(ramInMb);
                    put(Comparisons.GREATER_OR_EQUAL.getToscaName(), integerValue.toString());
                }};
                properties.put("mem_size", memSize);
            }
            if(component.isRealTime()!=null){
                Map<String,Object> realtime = new HashMap<String,Object>(){{
                    put(Comparisons.EQUAL.getToscaName(), component.isRealTime());
                }};
                properties.put("realtime", realtime);
            }

            // Preselected Domain (in the future it could be moved to semiautomatic mode)
            if(component.getDomain()!=null) {
                if (!component.getDomain().isBlank()) {
                    Map<String, Object> selectedDomain = new HashMap<String, Object>() {{
                        put(Comparisons.EQUAL.getToscaName(), component.getDomain());
                    }};
                    properties.put("domain_id", selectedDomain);
                }
            }

            // Energy fields are included as host properties
            // Check when energy fields are disabled in the frontend
            if (component.getGreenOptions()) {
                Map<String, Object> efficiency;
                if (component.getEnergyEfficiencyRatio() != null) {
                    efficiency = new HashMap<String, Object>() {{
                        Float energyEfficiencyRatio = component.getEnergyEfficiencyRatio() * 100;
                        Integer integerValue = Math.round(energyEfficiencyRatio);
                        put(Comparisons.GREATER_OR_EQUAL.getToscaName(), integerValue.toString());
                    }};
                } else {
                    efficiency = new HashMap<String, Object>() {{
                        put(Comparisons.GREATER_OR_EQUAL.getToscaName(), "0");
                    }};
                }
                properties.put("energy_efficiency", efficiency);

                if (component.getGreenEnergyRatio() != null) {
                    Map<String, Object> green = new HashMap<String, Object>() {{
                        Float greenEnergyRatio = component.getGreenEnergyRatio() * 100;
                        Integer integerValue = Math.round(greenEnergyRatio);
                        put(Comparisons.GREATER_OR_EQUAL.getToscaName(), integerValue.toString());
                    }};
                    properties.put("green", green);
                }
            }

            hostProperties.setProperties(properties);
            // Create node capabilities with host
            nodeCapabilities.add(new HashMap<String, Object>(){{
                put("host", hostProperties);
            }});
            nodeFilter.setCapabilities(nodeCapabilities);
        }
        return new HostRequirement(nodeFilter);
    }

    public Boolean checkDockerHub(String image) {
        logger.debug("Provided container image name: {}", image);
        if(image.contains(".") || image.contains(":") || image.equals("localhost")){
            return false;
        } else if (image.chars().filter(c->c=='/').count() > 1) {
            logger.debug("Number of slashes(/):{}", image.chars().filter(c -> c == '/').count());
            return false;
        }
        return true;
    }

}
