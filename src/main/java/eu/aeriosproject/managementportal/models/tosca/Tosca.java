package eu.aeriosproject.managementportal.models.tosca;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Tosca {
    @JsonProperty("tosca_definition_version")
    private String tosca_definitions_version;
    private String description;
    private boolean serviceOverlay;
    @JsonProperty("node_templates")
    private Map<String, ServiceComponent> node_templates;

    public String getTosca_definitions_version() {
        return tosca_definitions_version;
    }

    public void setTosca_definitions_version(String tosca_definitions_version) {
        this.tosca_definitions_version = tosca_definitions_version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isServiceOverlay() { return serviceOverlay; }

    public void setServiceOverlay(boolean serviceOverlay) { this.serviceOverlay = serviceOverlay; }

    public Map<String, ServiceComponent> getNode_templates() {
        return node_templates;
    }

    public void setNode_templates(Map<String, ServiceComponent> node_templates) {
        this.node_templates = node_templates;
    }
}
