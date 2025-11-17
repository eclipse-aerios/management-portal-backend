package eu.aeriosproject.managementportal.models.tosca;

import java.util.List;

public class NodeProperties {
    private List<String> id;

    public NodeProperties(List<String> id) {
        this.id = id;
    }

    public List<String> getId() {
        return id;
    }

    public void setId(List<String> id) {
        this.id = id;
    }
}
