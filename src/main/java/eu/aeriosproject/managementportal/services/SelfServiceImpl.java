package eu.aeriosproject.managementportal.services;

import eu.aeriosproject.managementportal.models.Domain;
import eu.aeriosproject.managementportal.models.InfrastructureElement;
import eu.aeriosproject.managementportal.models.SelfOptimizationAnomalyParameters;
import eu.aeriosproject.managementportal.models.SelfOptimizationAnomalyParametersType;
import eu.aeriosproject.managementportal.utils.NgsildConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SelfServiceImpl implements SelfService{
    private final String selfOptimizationPath = "/optimization";
    private final String optimizationAnomalyParametersPath = "/anomaly/parameters";
    @Autowired
    private DomainService domainService;
    @Autowired
    private IEService ieService;
    @Autowired
    @Qualifier("unsafeRestTemplate")
    private RestTemplate selfClient;

    @Override
    public SelfOptimizationAnomalyParameters GetSelfOptimizationAnomalyParameters(String ieId, SelfOptimizationAnomalyParametersType type) {
        String apiGatewayIeUrl = getApiGatewayIeUrl(ieId);
        String url = String.format("%s%s%s/%s", apiGatewayIeUrl, selfOptimizationPath, optimizationAnomalyParametersPath, type);
        return selfClient.getForObject(url, SelfOptimizationAnomalyParameters.class);
    }

    @Override
    public void UpdateSelfOptimizationAnomalyParameters(String ieId, SelfOptimizationAnomalyParametersType type, SelfOptimizationAnomalyParameters parameters) {
        String apiGatewayIeUrl = getApiGatewayIeUrl(ieId);
        String url = String.format("%s%s%s/%s", apiGatewayIeUrl, selfOptimizationPath, optimizationAnomalyParametersPath, type);
        selfClient.put(url, parameters);
    }

    private String getApiGatewayIeUrl(String ieId){
        InfrastructureElement ie = ieService.getById(ieId);
        // Get Domain public URL and make request to the proper KrakenD endpoint
        Domain domain = domainService.getById(ie.getDomain());
        String ieShortId = NgsildConverter.trimId(ie.getId()).replaceAll(NgsildConverter.trimId(domain.getId())+":", "");
        return String.format("%s/%s", domain.getPublicUrl(), ieShortId);
    }

}
