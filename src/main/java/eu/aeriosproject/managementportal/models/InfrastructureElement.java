package eu.aeriosproject.managementportal.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfrastructureElement {

    private String id;
    private String type;
    private String domain;
    private String hostname;
    private String internalIpAddress;
    private String macAddress;
    private Float trustScore;
    // TODO use enum
    private String containerTechnology;
    private String lowLevelOrchestrator;
    private Integer cpuCores;
    private Float cpuFreqMax;
    private Integer currentCpuUsage;
    private Integer ramCapacity;
    private Integer availableRam;
    private Integer currentRamUsage;
    private Integer currentRamUsagePct;
    private Boolean gpu;
    private Integer gpuMemory;
    private String diskType;
    private Integer diskCapacity;
    private Integer availableDisk;
    private Integer currentDiskUsage;
    private Integer currentDiskUsagePct;
    private Integer avgPowerConsumption;
    private Integer currentPowerConsumption;
    private String powerSource;
    private Float energyEfficiencyRatio;
    private Boolean realTimeCapable;
    private String cpuArchitecture;
    private String operatingSystem;
    private String infrastructureElementTier;
    private String infrastructureElementStatus;
    private Location location;

    public InfrastructureElement() {
    }

    public InfrastructureElement(String id, String type, String domain, String hostname, String internalIpAddress, String macAddress, Float trustScore, String containerTechnology, String lowLevelOrchestrator, Integer cpuCores, Float cpuFreqMax, Integer currentCpuUsage, Integer ramCapacity, Integer availableRam, Integer currentRamUsage, Integer currentRamUsagePct, Boolean gpu, Integer gpuMemory, String diskType, Integer diskCapacity, Integer availableDisk, Integer currentDiskUsage, Integer currentDiskUsagePct, Integer avgPowerConsumption, Integer currentPowerConsumption, String powerSource, Float energyEfficiencyRatio, Boolean realTimeCapable, String cpuArchitecture, String operatingSystem, String infrastructureElementTier, String infrastructureElementStatus, Location location) {
        this.id = id;
        this.type = type;
        this.domain = domain;
        this.hostname = hostname;
        this.internalIpAddress = internalIpAddress;
        this.macAddress = macAddress;
        this.trustScore = trustScore;
        this.containerTechnology = containerTechnology;
        this.lowLevelOrchestrator = lowLevelOrchestrator;
        this.cpuCores = cpuCores;
        this.cpuFreqMax = cpuFreqMax;
        this.currentCpuUsage = currentCpuUsage;
        this.ramCapacity = ramCapacity;
        this.availableRam = availableRam;
        this.currentRamUsage = currentRamUsage;
        this.currentRamUsagePct = currentRamUsagePct;
        this.gpu = gpu;
        this.gpuMemory = gpuMemory;
        this.diskType = diskType;
        this.diskCapacity = diskCapacity;
        this.availableDisk = availableDisk;
        this.currentDiskUsage = currentDiskUsage;
        this.currentDiskUsagePct = currentDiskUsagePct;
        this.avgPowerConsumption = avgPowerConsumption;
        this.currentPowerConsumption = currentPowerConsumption;
        this.powerSource = powerSource;
        this.energyEfficiencyRatio = energyEfficiencyRatio;
        this.realTimeCapable = realTimeCapable;
        this.cpuArchitecture = cpuArchitecture;
        this.operatingSystem = operatingSystem;
        this.infrastructureElementTier = infrastructureElementTier;
        this.infrastructureElementStatus = infrastructureElementStatus;
        this.location = location;
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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getInternalIpAddress() {
        return internalIpAddress;
    }

    public void setInternalIpAddress(String internalIpAddress) {
        this.internalIpAddress = internalIpAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getLowLevelOrchestrator() {
        return lowLevelOrchestrator;
    }

    public void setLowLevelOrchestrator(String lowLevelOrchestrator) {
        this.lowLevelOrchestrator = lowLevelOrchestrator;
    }

    public Integer getCpuCores() {
        return cpuCores;
    }

    public void setCpuCores(Integer cpuCores) {
        this.cpuCores = cpuCores;
    }

    public Integer getCurrentCpuUsage() {
        return currentCpuUsage;
    }

    public void setCurrentCpuUsage(Integer currentCpuUsage) {
        this.currentCpuUsage = currentCpuUsage;
    }

    public Integer getRamCapacity() {
        return ramCapacity;
    }

    public void setRamCapacity(Integer ramCapacity) {
        this.ramCapacity = ramCapacity;
    }

    public Integer getAvailableRam() {
        return availableRam;
    }

    public void setAvailableRam(Integer availableRam) {
        this.availableRam = availableRam;
    }

    public Integer getCurrentRamUsage() {
        return currentRamUsage;
    }

    public void setCurrentRamUsage(Integer currentRamUsage) {
        this.currentRamUsage = currentRamUsage;
    }

    public Integer getAvgPowerConsumption() {
        return avgPowerConsumption;
    }

    public void setAvgPowerConsumption(Integer avgPowerConsumption) {
        this.avgPowerConsumption = avgPowerConsumption;
    }

    public Integer getCurrentPowerConsumption() {
        return currentPowerConsumption;
    }

    public void setCurrentPowerConsumption(Integer currentPowerConsumption) {
        this.currentPowerConsumption = currentPowerConsumption;
    }

    public Boolean isRealTimeCapable() {
        return realTimeCapable;
    }

    public void setRealTimeCapable(Boolean realTimeCapable) {
        this.realTimeCapable = realTimeCapable;
    }

    public String getCpuArchitecture() {
        return cpuArchitecture;
    }

    public void setCpuArchitecture(String cpuArchitecture) {
        this.cpuArchitecture = cpuArchitecture;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getInfrastructureElementTier() {
        return infrastructureElementTier;
    }

    public void setInfrastructureElementTier(String infrastructureElementTier) {
        this.infrastructureElementTier = infrastructureElementTier;
    }

    public String getInfrastructureElementStatus() {
        return infrastructureElementStatus;
    }

    public void setInfrastructureElementStatus(String infrastructureElementStatus) {
        this.infrastructureElementStatus = infrastructureElementStatus;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Float getTrustScore() {
        return trustScore;
    }

    public void setTrustScore(Float trustScore) {
        this.trustScore = trustScore;
    }

    public String getContainerTechnology() {
        return containerTechnology;
    }

    public void setContainerTechnology(String containerTechnology) {
        this.containerTechnology = containerTechnology;
    }

    public Float getCpuFreqMax() {
        return cpuFreqMax;
    }

    public void setCpuFreqMax(Float cpuFreqMax) {
        this.cpuFreqMax = cpuFreqMax;
    }

    public Integer getCurrentRamUsagePct() {
        return currentRamUsagePct;
    }

    public void setCurrentRamUsagePct(Integer currentRamUsagePct) {
        this.currentRamUsagePct = currentRamUsagePct;
    }

    public Boolean isGpu() {
        return gpu;
    }

    public void setGpu(Boolean gpu) {
        this.gpu = gpu;
    }

    public Integer getGpuMemory() {
        return gpuMemory;
    }

    public void setGpuMemory(Integer gpuMemory) {
        this.gpuMemory = gpuMemory;
    }

    public String getDiskType() {
        return diskType;
    }

    public void setDiskType(String diskType) {
        this.diskType = diskType;
    }

    public Integer getDiskCapacity() {
        return diskCapacity;
    }

    public void setDiskCapacity(Integer diskCapacity) {
        this.diskCapacity = diskCapacity;
    }

    public Integer getAvailableDisk() {
        return availableDisk;
    }

    public void setAvailableDisk(Integer availableDisk) {
        this.availableDisk = availableDisk;
    }

    public Integer getCurrentDiskUsage() {
        return currentDiskUsage;
    }

    public void setCurrentDiskUsage(Integer currentDiskUsage) {
        this.currentDiskUsage = currentDiskUsage;
    }

    public Integer getCurrentDiskUsagePct() {
        return currentDiskUsagePct;
    }

    public void setCurrentDiskUsagePct(Integer currentDiskUsagePct) {
        this.currentDiskUsagePct = currentDiskUsagePct;
    }

    public String getPowerSource() {
        return powerSource;
    }

    public void setPowerSource(String powerSource) {
        this.powerSource = powerSource;
    }

    public Float getEnergyEfficiencyRatio() {
        return energyEfficiencyRatio;
    }

    public void setEnergyEfficiencyRatio(Float energyEfficiencyRatio) {
        this.energyEfficiencyRatio = energyEfficiencyRatio;
    }
}
