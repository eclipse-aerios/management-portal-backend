package eu.aeriosproject.managementportal.services;

import eu.aeriosproject.managementportal.models.InfrastructureElement;
import eu.aeriosproject.managementportal.utils.NgsildConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class IEServiceImpl implements IEService{
    @Value("${aerios.orion-ld}")
    private String ORION_URL;

    @Autowired
    @Qualifier("orionRestTemplate")
    private RestTemplate orionClient;

    public List<InfrastructureElement> list() {
        String attributes = "hostname,lowLevelOrchestrator,cpuCores,cpuArchitecture,ramCapacity,realTimeCapable,infrastructureElementStatus,containerTechnology";
        ResponseEntity<InfrastructureElement[]> response = orionClient
                .getForEntity(ORION_URL+"?type=InfrastructureElement&format=simplified&attrs="+ attributes, InfrastructureElement[].class);
        return Arrays.asList(response.getBody());
    }

    @Override
    public InfrastructureElement getById(String ieId) {
        ieId = NgsildConverter.getInfrastructureElementId(ieId);
        ResponseEntity<InfrastructureElement> response = orionClient
                .getForEntity(ORION_URL+"/{ieId}?format=simplified", InfrastructureElement.class, ieId);
        return response.getBody();
    }
}
