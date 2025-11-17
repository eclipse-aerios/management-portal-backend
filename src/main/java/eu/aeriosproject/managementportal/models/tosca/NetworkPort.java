package eu.aeriosproject.managementportal.models.tosca;

public class NetworkPort {
    PortProperties properties;

    public NetworkPort(PortProperties properties) {
        this.properties = properties;
    }

    public PortProperties getProperties() {
        return properties;
    }

    public void setProperties(PortProperties properties) {
        this.properties = properties;
    }
}
