package eu.aeriosproject.managementportal.models.tosca;

import java.util.Map;

public class Create {
    private String implementation = "application_image";
    private Inputs inputs;

    public String getImplementation() {
        return implementation;
    }

    public void setImplementation(String implementation) {
        this.implementation = implementation;
    }

    public Inputs getInputs() {
        return inputs;
    }

    public void setInputs(Inputs inputs) {
        this.inputs = inputs;
    }
}
