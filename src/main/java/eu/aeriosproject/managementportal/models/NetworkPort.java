package eu.aeriosproject.managementportal.models;

public class NetworkPort {

    private String portProtocol;
    private int portNumber;
    private int externalPortNumber;
    private boolean exposed;

    public NetworkPort() {
    }

    public NetworkPort(String portProtocol, int portNumber, int externalPortNumber, boolean exposed) {
        this.portProtocol = portProtocol;
        this.portNumber = portNumber;
        this.externalPortNumber = externalPortNumber;
        this.exposed = exposed;
    }

    public String getPortProtocol() {
        return portProtocol;
    }

    public void setPortProtocol(String portProtocol) {
        this.portProtocol = portProtocol;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public boolean getExposed() { return exposed; }

    public void setExposed(boolean exposed) { this.exposed = exposed; }

    public int getExternalPortNumber() { return externalPortNumber;}

    public void setExternalPortNumber(int externalPortNumber) { this.externalPortNumber = externalPortNumber; }
}
