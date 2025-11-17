package eu.aeriosproject.managementportal.models.serviceform;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Components {
    private String name;
    private String type;
    private String description;
    private String containerImage;
    @JsonProperty("selected_ie")
    private String selectedIe;
    private Float cpu;
    private Float ram;
    @JsonProperty("cpu_arch")
    private String cpuArch;
    @JsonProperty("real_time")
    private Boolean realTime;
    private List<Argument> arguments;
    private List<NetworkPort> networkPorts;
    private boolean greenOptions;
    private Float energyEfficiencyRatio;
    private Float greenEnergyRatio;
    @JsonProperty("expose_ports")
    private boolean exposePorts;
    private boolean deployAsJob;
    private PrivateImage privateImage;
    private boolean isPrivateImage;
    private List<String> iePool;
    private String domain;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContainerImage() {
        return containerImage;
    }

    public void setContainerImage(String containerImage) {
        this.containerImage = containerImage;
    }

    public String getSelectedIe() {
        return selectedIe;
    }

    public void setSelectedIe(String selectedIe) {
        this.selectedIe = selectedIe;
    }

    public Float getCpu() {
        return cpu;
    }

    public void setCpu(Float cpu) {
        this.cpu = cpu;
    }

    public Float getRam() {
        return ram;
    }

    public void setRam(Float ram) {
        this.ram = ram;
    }

    public String getCpuArch() {
        return cpuArch;
    }

    public void setCpuArch(String cpuArch) {
        this.cpuArch = cpuArch;
    }

    public Boolean isRealTime() {
        return realTime;
    }

    public void setRealTime(Boolean realTime) {
        this.realTime = realTime;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public void setArguments(List<Argument> arguments) {
        this.arguments = arguments;
    }

    public List<NetworkPort> getNetworkPorts() {
        return networkPorts;
    }

    public void setNetworkPorts(List<NetworkPort> networkPorts) {
        this.networkPorts = networkPorts;
    }

    public boolean getGreenOptions() { return greenOptions; }

    public void setGreenOptions(boolean greenOptions) { this.greenOptions = greenOptions; }

    public Float getEnergyEfficiencyRatio() {
        return energyEfficiencyRatio;
    }

    public void setEnergyEfficiencyRatio(Float energyEfficiencyRatio) {
        this.energyEfficiencyRatio = energyEfficiencyRatio;
    }

    public Float getGreenEnergyRatio() {
        return greenEnergyRatio;
    }

    public void setGreenEnergyRatio(Float greenEnergyRatio) {
        this.greenEnergyRatio = greenEnergyRatio;
    }

    public boolean getExposePorts() {
        return exposePorts;
    }

    public void setExposePorts(boolean exposePorts) {
        this.exposePorts = exposePorts;
    }

    public boolean getDeployAsJob() {
        return deployAsJob;
    }

    public void setDeployAsJob(boolean deployAsJob) {
        this.deployAsJob = deployAsJob;
    }

    @JsonProperty("isPrivateImage")
    public boolean getIsPrivateImage() {
        return isPrivateImage;
    }

    @JsonProperty("isPrivateImage")
    public void setIsPrivateImage(boolean isPrivateImage) {
        this.isPrivateImage = isPrivateImage;
    }

    public PrivateImage getPrivateImage() {
        return privateImage;
    }

    public void setPrivateImage(PrivateImage privateImage) {
        this.privateImage = privateImage;
    }

    public List<String> getIePool() {
        return iePool;
    }

    public void setIePool(List<String> iePool) {
        this.iePool = iePool;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomainId(String domain) {
        this.domain = domain;
    }
}
