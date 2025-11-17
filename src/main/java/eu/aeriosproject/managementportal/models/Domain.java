package eu.aeriosproject.managementportal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Domain {
    private String id;
    private String type;
    private String description;
    private String publicUrl;
    private ArrayList<String> owner;
    @JsonProperty("isEntrypoint")
    private Boolean isEntrypoint;
    private String domainStatus;
    private String publicKey;

    public Domain() {
    }

    public Domain(String id, String type, String description, String publicUrl, ArrayList<String> owner, Boolean isEntrypoint, String domainStatus, String publicKey) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.publicUrl = publicUrl;
        this.owner = owner;
        this.isEntrypoint = isEntrypoint;
        this.domainStatus = domainStatus;
        this.publicKey = publicKey;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublicUrl() {
        return publicUrl;
    }

    public void setPublicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
    }

    public ArrayList<String> getOwner() {
        return owner;
    }

    public void setOwner(ArrayList<String> owner) {
        this.owner = owner;
    }

    public Boolean isEntrypoint() {
        return isEntrypoint;
    }

    public void setEntrypoint(Boolean isEntrypoint) {
        this.isEntrypoint = isEntrypoint;
    }

    public String getDomainStatus() {
        return domainStatus;
    }

    public void setDomainStatus(String domainStatus) {
        this.domainStatus = domainStatus;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
