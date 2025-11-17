package eu.aeriosproject.managementportal.models.tosca;

public enum PortProtocol {
    TCP,
    UDP,
    SCTP;

    public String getToscaName() {
        return this.name().toLowerCase();
    }
}
