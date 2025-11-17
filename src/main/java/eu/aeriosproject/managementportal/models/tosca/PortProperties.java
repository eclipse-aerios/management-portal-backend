package eu.aeriosproject.managementportal.models.tosca;

import java.util.List;

public class PortProperties {
    private List<String> protocol;
    private int source;

    public PortProperties(List<String> protocol, int source) {
        this.protocol = protocol;
        this.source = source;
    }

    public PortProperties() {
    }

    public List<String> getProtocol() {
        return protocol;
    }

    public void setProtocol(List<String> protocol) {
        this.protocol = protocol;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }
}
