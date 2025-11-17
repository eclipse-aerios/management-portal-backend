package eu.aeriosproject.managementportal.models.tosca;

import java.util.Map;

public class NetworkProperties {
    private Map<String, NetworkPort> ports;
    private boolean exposePorts;

    public Map<String, NetworkPort> getPorts() {
        return ports;
    }

    public void setPorts(Map<String, NetworkPort> ports) {
        this.ports = ports;
    }

    public boolean isExposePorts() {
        return exposePorts;
    }

    public void setExposePorts(boolean exposePorts) {
        this.exposePorts = exposePorts;
    }
}
