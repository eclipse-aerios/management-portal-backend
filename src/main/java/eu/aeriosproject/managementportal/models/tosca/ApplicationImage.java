package eu.aeriosproject.managementportal.models.tosca;

import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationImage {
    private String file;
    private String type = "tosca.artifacts.Deployment.Image.Container.Docker";
    private String repository;
    @JsonProperty("isPrivate")
    private boolean isPrivate;
    private String username;
    private String password;

    public ApplicationImage(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    @JsonProperty("isPrivate")
    public boolean getIsPrivate() {
        return isPrivate;
    }

    @JsonProperty("isPrivate")
    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
