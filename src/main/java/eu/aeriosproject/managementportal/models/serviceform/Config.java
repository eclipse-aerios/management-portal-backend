package eu.aeriosproject.managementportal.models.serviceform;

public class Config {
    private String name;
    private String description;
    private boolean serviceOverlay;

    public Config() {
    }

    public Config(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isServiceOverlay() {
        return serviceOverlay;
    }

    public void setServiceOverlay(boolean serviceOverlay) {
        this.serviceOverlay = serviceOverlay;
    }
}
