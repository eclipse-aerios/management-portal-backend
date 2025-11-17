package eu.aeriosproject.managementportal.models.tosca;

import java.util.List;
import java.util.Map;

public class NodeCapabilities {
    private List<Map<String, HostProperties>> properties;

    public List<Map<String, HostProperties>> getProperties() {
        return properties;
    }

    public void setProperties(List<Map<String, HostProperties>> properties) {
        this.properties = properties;
    }
}
