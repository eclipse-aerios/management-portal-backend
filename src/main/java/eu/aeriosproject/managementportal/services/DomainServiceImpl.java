package eu.aeriosproject.managementportal.services;

import eu.aeriosproject.managementportal.models.Domain;
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
public class DomainServiceImpl implements DomainService {

    @Value("${aerios.orion-ld}")
    private String ORION_URL;

    @Autowired
    @Qualifier("orionRestTemplate")
    private RestTemplate orionClient;

    @Override
    public List<Domain> list() {
        ResponseEntity<Domain[]> response = orionClient
                .getForEntity(ORION_URL+"?type=Domain&format=simplified", Domain[].class);
        return Arrays.asList(response.getBody());
    }

    @Override
    public Domain getById(String domainId) {
        domainId = NgsildConverter.getDomainId(domainId);
        ResponseEntity<Domain> response = orionClient
                .getForEntity(ORION_URL+"/{domainId}?format=simplified", Domain.class, domainId);
        return response.getBody();
    }

    @Override
    public Domain getEntrypointDomain(Boolean reduced) {
        RestTemplate localOrionClient = new RestTemplate();
        ResponseEntity<Domain[]> response = localOrionClient
                .getForEntity(ORION_URL+"?type=Domain&format=simplified&local=true", Domain[].class);
        response.getBody();
        Domain entrypoint = Arrays.asList(response.getBody()).get(0);

        if(reduced){
            Domain entrypointReduced = new Domain();
            entrypointReduced.setId(NgsildConverter.trimId(entrypoint.getId()));
            entrypointReduced.setDomainStatus(NgsildConverter.trimId(entrypoint.getDomainStatus()));
            return entrypointReduced;
        }
        entrypoint.setId(NgsildConverter.trimId(entrypoint.getId()));
        return entrypoint;
    }

    @Override
    public List<InfrastructureElement> getDomainIEs(String domainId) {
            String domainQuery = "domain==\""+NgsildConverter.getDomainId(domainId)+"\"";
            ResponseEntity<InfrastructureElement[]> response = orionClient
                    .getForEntity(ORION_URL+"?type=InfrastructureElement&format=simplified&q="+domainQuery, InfrastructureElement[].class);
            return Arrays.asList(response.getBody());
    }
}
