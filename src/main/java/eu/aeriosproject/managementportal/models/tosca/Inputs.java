package eu.aeriosproject.managementportal.models.tosca;

import java.util.List;
import java.util.Map;

public class Inputs {
    private List<Map<String, String>> cliArgs;
    private List<Map<String, String>> envVars;

    public List<Map<String, String>> getCliArgs() {
        return cliArgs;
    }

    public void setCliArgs(List<Map<String, String>> cliArgs) {
        this.cliArgs = cliArgs;
    }

    public List<Map<String, String>> getEnvVars() {
        return envVars;
    }

    public void setEnvVars(List<Map<String, String>> envVars) {
        this.envVars = envVars;
    }
}
