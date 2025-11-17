package eu.aeriosproject.managementportal.services;

import eu.aeriosproject.managementportal.models.serviceform.Components;
import eu.aeriosproject.managementportal.models.serviceform.NewDeployment;
import eu.aeriosproject.managementportal.models.tosca.*;

import java.util.List;
import java.util.Map;

public interface ToscaService {
    public Tosca buildTosca(NewDeployment newDeployment);
    public Interfaces createInterfaces(Components component);
    public Artifacts createArtifacts(Components component);
    public List<Map<String, Requirement>> createRequirements(OrchestrationMode mode, Components component);
    public ServiceComponent createNodeTemplate(Artifacts artifacts, Interfaces interfaces, List<Map<String, Requirement>> requirements, boolean isJob);
}
