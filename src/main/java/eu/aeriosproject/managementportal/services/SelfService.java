package eu.aeriosproject.managementportal.services;

import eu.aeriosproject.managementportal.models.SelfOptimizationAnomalyParameters;
import eu.aeriosproject.managementportal.models.SelfOptimizationAnomalyParametersType;

public interface SelfService {
    SelfOptimizationAnomalyParameters GetSelfOptimizationAnomalyParameters(String ieId, SelfOptimizationAnomalyParametersType type);
    void UpdateSelfOptimizationAnomalyParameters(String ieId, SelfOptimizationAnomalyParametersType type, SelfOptimizationAnomalyParameters parameters);
}
