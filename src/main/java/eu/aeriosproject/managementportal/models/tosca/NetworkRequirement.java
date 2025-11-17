package eu.aeriosproject.managementportal.models.tosca;

public class NetworkRequirement extends Requirement{
    private NetworkProperties properties;

    public NetworkProperties getProperties() {
        return properties;
    }

    public void setProperties(NetworkProperties properties) {
        this.properties = properties;
    }
}
