package eu.aeriosproject.managementportal.models.tosca;

public enum Comparisons {
    GREATER_OR_EQUAL,
    GREATER_THAN,
    EQUAL,
    LESS_THAN,
    LESS_OR_EQUAL,
    IN_RANGE;

    public String getToscaName() {
        return this.name().toLowerCase();
    }
}
