package eu.aeriosproject.managementportal.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.aeriosproject.managementportal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BenchmarkingKpiServiceImpl implements BenchmarkingKpiService{
    @Value("${aerios.orion-ld}")
    private String ORION_URL;

    private static final List<String> ARCHITECTURES = List.of("x64", "arm64");

    @Autowired
    @Qualifier("orionRestTemplate")
    private RestTemplate orionClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JsonNode getNumberOfFederatedDomains() {
        int count = getCountByEntityType("Domain");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode().put("domains", count);
    }

    @Override
    public JsonNode getNumberOfInfrastructureElements() {
        int count = getCountByEntityType("InfrastructureElement");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode().put("ies", count);
    }

    @Override
    public JsonNode getNumberOfContainerManagementFrameworks() {
        ArrayNode result = objectMapper.createArrayNode();

        ResponseEntity<List<JsonNode>> response = orionClient.exchange(
                ORION_URL + "?format=simplified&type=LowLevelOrchestrator&count=true",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<JsonNode>>() {}
        );
        Set<String> orchestrationTypes = response.getBody().stream()
                .filter(node -> node.has("orchestrationType"))
                .map(node -> node.get("orchestrationType").asText())
                .collect(Collectors.toSet());

        int totalIEs = getCountByEntityType("InfrastructureElement");

        orchestrationTypes.stream().map(orchestrationType -> {
            String typeName = orchestrationType.substring(orchestrationType.lastIndexOf(":") + 1);

            String urlFramework = ORION_URL
                    + "?format=simplified&count=true"
                    + "&attrs=containerTechnology,lowLevelOrchestrator"
                    + "&type=InfrastructureElement"
                    + "&q=containerTechnology~=.*" + typeName;

            ResponseEntity<JsonNode> res = orionClient.getForEntity(urlFramework, JsonNode.class);

            int frameworkCount = res.getBody() != null ? getIntHeaderIgnoreCase(res.getHeaders(), "Ngsild-Results-Count") : 0;

            double percentage = totalIEs > 0 ? (frameworkCount * 100.0 / totalIEs) : 0.0;

            ObjectNode usage = objectMapper.createObjectNode();
            usage.put("frameworkName", typeName);
            usage.put("count", frameworkCount);
            usage.put("percentage", percentage);
            return usage;
        }).forEach(result::add);

        return result;
    }

    @Override
    public JsonNode getNumberOfCpuArchitectures() {
        int totalIEs = getCountByEntityType("InfrastructureElement");
        ArrayNode result = objectMapper.createArrayNode();

        ARCHITECTURES.stream().map(arch -> {
            String urlArch = ORION_URL
                    + "?format=simplified&count=true"
                    + "&type=InfrastructureElement"
                    + "&attrs=cpuArchitecture"
                    + "&q=cpuArchitecture~=.*" + arch;

            ResponseEntity<JsonNode> res = orionClient.getForEntity(urlArch, JsonNode.class);

            // int archCount = res.getBody() != null && !res.getHeaders().getFirst("Ngsild-Results-Count").isEmpty() ? Integer.parseInt(res.getHeaders().getFirst("Ngsild-Results-Count")) : 0;
            int archCount = res.getBody() != null ? getIntHeaderIgnoreCase(res.getHeaders(), "Ngsild-Results-Count") : 0;
            double percentage = totalIEs > 0 ? (archCount * 100.0 / totalIEs) : 0.0;

            ObjectNode usage = objectMapper.createObjectNode();
            usage.put("cpuArchitecture", arch);
            usage.put("count", archCount);
            usage.put("percentage", percentage);

            return usage;
        }).forEach(result::add);

        return result;
    }

    @Override
    public JsonNode getNumberOfUserServices() {
        int count = getCountByEntityType("Service");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode().put("services", count);
    }

    @Override
    public JsonNode getNumberOfServicesComponents() {
        int count = getCountByEntityType("ServiceComponent", "q=serviceComponentStatus!=\"urn:ngsi-ld:ServiceComponentStatus:Failed\"");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode().put("service_components", count);
    }

    @Override
    public JsonNode getNumberOfAuthenticatedUsers() {
        int count = userRepository.getUsers().size();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode().put("authenticated_users", count);
    }

    private int getCountByEntityType(String entityType, String... additionalAttrs) {
        StringBuilder urlBuilder = new StringBuilder(ORION_URL)
                .append("?format=simplified&type=")
                .append(entityType)
                .append("&count=true");

        if (additionalAttrs.length > 0) {
            urlBuilder.append("&");
            for (String attr : additionalAttrs) {
                urlBuilder.append(attr).append(",");
            }
            urlBuilder.setLength(urlBuilder.length() - 1);
        }

        ResponseEntity<JsonNode> response = orionClient.getForEntity(urlBuilder.toString(), JsonNode.class);
        return response.getBody() != null ? getIntHeaderIgnoreCase(response.getHeaders(), "Ngsild-Results-Count") : 0;
    }

    private int getIntHeaderIgnoreCase(HttpHeaders headers, String headerName) {
        String header = headers.getFirst(headerName);
        return (header != null) ? Integer.parseInt(header) : 0;
    }
}
