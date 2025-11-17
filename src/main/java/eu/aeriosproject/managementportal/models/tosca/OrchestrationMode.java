package eu.aeriosproject.managementportal.models.tosca;

public enum OrchestrationMode {
    MANUAL,
    SEMIAUTO,
    AUTO;

    public String getModeName() { return this.name().toLowerCase(); }
}
