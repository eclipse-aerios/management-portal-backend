package eu.aeriosproject.managementportal.services;

import com.fasterxml.jackson.databind.JsonNode;

public interface BenchmarkingKpiService {
    public JsonNode getNumberOfFederatedDomains();
    public JsonNode getNumberOfInfrastructureElements();
    public JsonNode getNumberOfContainerManagementFrameworks();
    public JsonNode getNumberOfCpuArchitectures();
    public JsonNode getNumberOfUserServices();
    public JsonNode getNumberOfServicesComponents();
    public JsonNode getNumberOfAuthenticatedUsers();
}
