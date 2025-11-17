package eu.aeriosproject.managementportal.models.tosca;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class ServiceComponent {
    private String type = "tosca.nodes.Container.Application";
    @JsonProperty("isJob")
    private boolean job;
    private Artifacts artifacts;
    private Interfaces interfaces;
    private List<Map<String, Requirement>> requirements;

    public ServiceComponent(Artifacts artifacts, Interfaces interfaces, List<Map<String, Requirement>> requirements, boolean job) {
        this.artifacts = artifacts;
        this.interfaces = interfaces;
        this.requirements = requirements;
        this.job = job;
    }

    public ServiceComponent() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("isJob")
    public boolean getIsJob() { return job; }

    @JsonProperty("isJob")
    public void setIsJob(boolean isJob) { this.job = isJob; }

    public Artifacts getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(Artifacts artifacts) {
        this.artifacts = artifacts;
    }

    public Interfaces getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(Interfaces interfaces) {
        this.interfaces = interfaces;
    }

    public List<Map<String, Requirement>> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Map<String, Requirement>> requirements) {
        this.requirements = requirements;
    }
}
