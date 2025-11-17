package eu.aeriosproject.managementportal.models;

import java.util.ArrayList;

public class ServiceComponentEntity {
    private String id;
    private String type;
    private String infrastructureElement;
    private String service;
    private String serviceComponentStatus;
    private String infrastructureElementRequirements;
    private ArrayList<KeyValue> sla;
    private String containerImage;
    private ArrayList<KeyValue> cliArgs;
    private ArrayList<KeyValue> envVars;
    private ArrayList<String> networkPorts;
    private boolean exposePorts;

    public ServiceComponentEntity() {
    }

    public ServiceComponentEntity(String id, String type, String infrastructureElement, String service, String serviceComponentStatus, String infrastructureElementRequirements, ArrayList<KeyValue> sla, String containerImage, ArrayList<KeyValue> cliArgs, ArrayList<KeyValue> envVars, ArrayList<String> networkPorts, boolean exposePorts) {
        this.id = id;
        this.type = type;
        this.infrastructureElement = infrastructureElement;
        this.service = service;
        this.serviceComponentStatus = serviceComponentStatus;
        this.infrastructureElementRequirements = infrastructureElementRequirements;
        this.sla = sla;
        this.containerImage = containerImage;
        this.cliArgs = cliArgs;
        this.envVars = envVars;
        this.networkPorts = networkPorts;
        this.exposePorts = exposePorts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfrastructureElement() {
        return infrastructureElement;
    }

    public void setInfrastructureElement(String infrastructureElement) {
        this.infrastructureElement = infrastructureElement;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getServiceComponentStatus() {
        return serviceComponentStatus;
    }

    public void setServiceComponentStatus(String serviceComponentStatus) {
        this.serviceComponentStatus = serviceComponentStatus;
    }

    public String getInfrastructureElementRequirements() {
        return infrastructureElementRequirements;
    }

    public void setInfrastructureElementRequirements(String infrastructureElementRequirements) {
        this.infrastructureElementRequirements = infrastructureElementRequirements;
    }

    public ArrayList<KeyValue> getSla() {
        return sla;
    }

    public void setSla(ArrayList<KeyValue> sla) {
        this.sla = sla;
    }

    public String getContainerImage() {
        return containerImage;
    }

    public void setContainerImage(String containerImage) {
        this.containerImage = containerImage;
    }

    public ArrayList<KeyValue> getCliArgs() {
        return cliArgs;
    }

    public void setCliArgs(ArrayList<KeyValue> cliArgs) {
        this.cliArgs = cliArgs;
    }

    public ArrayList<KeyValue> getEnvVars() {
        return envVars;
    }

    public void setEnvVars(ArrayList<KeyValue> envVars) {
        this.envVars = envVars;
    }

    public ArrayList<String> getNetworkPorts() {
        return networkPorts;
    }

    public void setNetworkPorts(ArrayList<String> networkPorts) {
        this.networkPorts = networkPorts;
    }

    public boolean getExposePorts() { return exposePorts; }

    public void setExposePorts(boolean exposePorts) { this.exposePorts = exposePorts; }
}
