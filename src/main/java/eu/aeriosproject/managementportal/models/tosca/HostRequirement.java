package eu.aeriosproject.managementportal.models.tosca;

public class HostRequirement extends Requirement{
    private NodeFilter node_filter;

    public HostRequirement(NodeFilter node_filter) {
        this.node_filter = node_filter;
    }
    public NodeFilter getNode_filter() {
        return node_filter;
    }

    public void setNode_filter(NodeFilter node_filter) {
        this.node_filter = node_filter;
    }
}
