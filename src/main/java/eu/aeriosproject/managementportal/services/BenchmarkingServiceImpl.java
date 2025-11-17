package eu.aeriosproject.managementportal.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.aeriosproject.managementportal.models.BenchmarkType;
import eu.aeriosproject.managementportal.models.Domain;
import eu.aeriosproject.managementportal.models.InfrastructureElement;
import eu.aeriosproject.managementportal.utils.NgsildConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BenchmarkingServiceImpl implements BenchmarkingService {
    private final String path = "/benchmark/v1";
    @Value("${aerios.orion-ld}")
    private String ORION_URL;

    @Autowired
    private DomainService domainService;

    @Autowired
    private IEService ieService;

    @Autowired
    @Qualifier("unsafeRestTemplate")
    private RestTemplate benchmarkClient;

    @Autowired
    @Qualifier("orionRestTemplate")
    private RestTemplate orionClient;

    public JsonNode runBenchmarking(BenchmarkType benchmarkType, String ieId) {
        InfrastructureElement ie = ieService.getById(ieId);
        // Get Domain public URL and make request to the proper KrakenD endpoint
        Domain domain = domainService.getById(ie.getDomain());
        String ieID = NgsildConverter.trimId(ie.getId()).replaceAll(NgsildConverter.trimId(domain.getId())+":", "");
        String url = domain.getPublicUrl() + "/" + ieID + path +  "/" + benchmarkType.getBenchmarkType().toLowerCase();

        // Send POST request to the Benchmarking agent of the IE
        String response = benchmarkClient.postForObject(url, ie, String.class);

        // Convert response into JSON
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = null;
        try {
            jsonResponse = mapper.readTree(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    @Override
    public JsonNode getBenchmarking(String benchmarkId) {
        benchmarkId = NgsildConverter.getBenchmarkId(benchmarkId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<JsonNode> response = orionClient.exchange(
                ORION_URL + "/{benchmarkId}?format=simplified&options=sysAttrs",
                HttpMethod.GET,
                entity,
                JsonNode.class,
                benchmarkId
        );
        return response.getBody();
    }

    @Override
    public JsonNode getBenchmarksByIE(String ieId, BenchmarkType benchmarkType) {
        String query = String.format("&q=infrastructureElement==\"%s\"", ieId);
        if (benchmarkType != null) {
            query += String.format(";category==\"%s\"", benchmarkType.getBenchmarkType());
        }
        ResponseEntity<JsonNode[]> response = orionClient
                .getForEntity(ORION_URL + "?type=Benchmark&format=simplified&options=sysAttrs" + query, JsonNode[].class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = null;
        try {
            jsonResponse = mapper.valueToTree(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    public JsonNode addFromGeekbenchUrl(String geekBenchUrl) {
        if (isBenchmarkPresent(geekBenchUrl)) {
            return null;
        }

        Map<String, String> body = new HashMap<>();
        body.put("url", geekBenchUrl);

        Domain domain = domainService.getEntrypointDomain(false);
        List<InfrastructureElement> iEs = domainService.getDomainIEs(domain.getId());
        InfrastructureElement targetIE = iEs.getFirst();
        String ieID = NgsildConverter.trimId(targetIE.getId()).replaceAll(NgsildConverter.trimId(domain.getId())+":", "");
        String url = domain.getPublicUrl() + "/" + ieID + "/benchmark/v1/cpu?test=true"; // or ie.getHostname()

        String response = benchmarkClient.postForObject(url, body, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = null;
        try {
            jsonResponse = mapper.readTree(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    private boolean isBenchmarkPresent(String geekBenchUrl) {
        String query = String.format("&count=true&q=data[url]==\"%s\"", geekBenchUrl);
        ResponseEntity<JsonNode[]> response = orionClient
                .getForEntity(ORION_URL + "?type=Benchmark" + query, JsonNode[].class);
        int resultsCount = 0;
        if (!response.getHeaders().isEmpty()) {
            String resultsHeader = response.getHeaders().getFirst("Ngsild-Results-Count");
            if (resultsHeader != null) resultsCount = Integer.parseInt(resultsHeader);
        }
        return resultsCount > 0;
    }

    @Override
    public JsonNode getBenchmarksWithoutInfrastructureElement() {
        String query = "&q=!infrastructureElement";
        ResponseEntity<JsonNode[]> response = orionClient
                .getForEntity(ORION_URL + "?type=Benchmark&options=sysAttrs" + query, JsonNode[].class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = null;
        try {
            jsonResponse = mapper.valueToTree(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    @Override
    public JsonNode getBenchmarksByIE(String ieId) {
        return getBenchmarksByIE(ieId, null);
    }
}
