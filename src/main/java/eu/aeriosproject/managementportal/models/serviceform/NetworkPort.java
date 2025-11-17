package eu.aeriosproject.managementportal.models.serviceform;

public class NetworkPort {
    private String protocol;
    private int portNumber;

    public NetworkPort() {
    }

    public NetworkPort(String protocol, int portNumber) {
        this.protocol = protocol;
        this.portNumber = portNumber;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPort(int portNumber) {
        this.portNumber = portNumber;
    }
}
