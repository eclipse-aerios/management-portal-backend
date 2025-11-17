package eu.aeriosproject.managementportal.models.tosca;

import java.util.Map;

public class HostProperties {
    private Map<String,Map<String,Object>> properties;
    // TODO change if we include area

    public Map<String, Map<String, Object>> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Map<String, Object>> properties) {
        this.properties = properties;
    }
}
