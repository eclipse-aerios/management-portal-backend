package eu.aeriosproject.managementportal.models.serviceform;

import java.util.List;

public class NewDeployment {
    private Config config;
    private List<Components> serviceComponents;

    public NewDeployment() {
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public List<Components> getServiceComponents() {
        return serviceComponents;
    }

    public void setServiceComponents(List<Components> serviceComponents) {
        this.serviceComponents = serviceComponents;
    }
}
