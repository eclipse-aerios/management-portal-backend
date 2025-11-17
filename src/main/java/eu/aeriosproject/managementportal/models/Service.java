package eu.aeriosproject.managementportal.models;

public class Service {
    private String id;
    private String type;
    private String name;
    private String serviceType;
    private String description;
    private String domainHandler;
    private String actionType;

    public Service() {
    }

    public Service(String id, String type, String name, String serviceType, String description, String domainHandler, String actionType) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.serviceType = serviceType;
        this.description = description;
        this.domainHandler = domainHandler;
        this.actionType = actionType;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDomainHanler() {
        return domainHandler;
    }

    public void setDomainHandler(String domainHandler) {
        this.domainHandler = domainHandler;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
}
