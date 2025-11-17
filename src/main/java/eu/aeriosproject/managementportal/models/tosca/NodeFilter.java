package eu.aeriosproject.managementportal.models.tosca;

import java.util.List;
import java.util.Map;

public class NodeFilter {
    private NodeProperties properties;
    private List<Map<String, Object>> capabilities;

    public NodeProperties getProperties() {
        return properties;
    }

    public void setProperties(NodeProperties properties) {
        this.properties = properties;
    }

    public List<Map<String, Object>> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<Map<String, Object>> capabilities) {
        this.capabilities = capabilities;
    }

}
