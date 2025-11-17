package eu.aeriosproject.managementportal.models.tosca;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Artifacts {
    private ApplicationImage application_image;

    @JsonProperty("application_image")
    public ApplicationImage getApplication_image() {
        return application_image;
    }

    public void setApplication_image(ApplicationImage application_image) {
        this.application_image = application_image;
    }
}
