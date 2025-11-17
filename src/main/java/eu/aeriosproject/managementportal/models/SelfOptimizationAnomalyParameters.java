package eu.aeriosproject.managementportal.models;

public class SelfOptimizationAnomalyParameters {
    private SelfOptimizationAnomalyParametersType name;
    private Float toleranceThresholdAnomaly;
    private Float toleranceThresholdNormal;
    private Integer windowAnomaly;
    private Integer windowNormal;

    public SelfOptimizationAnomalyParameters() {
    }

    public SelfOptimizationAnomalyParameters(SelfOptimizationAnomalyParametersType name, Float toleranceThresholdAnomaly, Float toleranceThresholdNormal, Integer windowAnomaly, Integer windowNormal) {
        this.name = name;
        this.toleranceThresholdAnomaly = toleranceThresholdAnomaly;
        this.toleranceThresholdNormal = toleranceThresholdNormal;
        this.windowAnomaly = windowAnomaly;
        this.windowNormal = windowNormal;
    }

    public SelfOptimizationAnomalyParametersType getName() {
        return name;
    }

    public void setName(SelfOptimizationAnomalyParametersType name) {
        this.name = name;
    }

    public Float getToleranceThresholdAnomaly() {
        return toleranceThresholdAnomaly;
    }

    public void setToleranceThresholdAnomaly(Float toleranceThresholdAnomaly) {
        this.toleranceThresholdAnomaly = toleranceThresholdAnomaly;
    }

    public Float getToleranceThresholdNormal() {
        return toleranceThresholdNormal;
    }

    public void setToleranceThresholdNormal(Float toleranceThresholdNormal) {
        this.toleranceThresholdNormal = toleranceThresholdNormal;
    }

    public Integer getWindowAnomaly() {
        return windowAnomaly;
    }

    public void setWindowAnomaly(Integer windowAnomaly) {
        this.windowAnomaly = windowAnomaly;
    }

    public Integer getWindowNormal() {
        return windowNormal;
    }

    public void setWindowNormal(Integer windowNormal) {
        this.windowNormal = windowNormal;
    }
}
