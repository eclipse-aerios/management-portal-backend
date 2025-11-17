package eu.aeriosproject.managementportal.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NgsiLdBenchmarkNotificationDataItem extends  NgsiLdSubscriptionNotificationDataItem{
    private String infrastructureElement;
    private String category;
    private String status;

    public NgsiLdBenchmarkNotificationDataItem() {
        super();
    }
    @JsonCreator
    public NgsiLdBenchmarkNotificationDataItem(
            @JsonProperty("id") String id,
            @JsonProperty("type") String type,
            @JsonProperty("infrastructureElement") String infrastructureElement,
            @JsonProperty("category") String category,
            @JsonProperty("status")String status)
    {
        super(id, type);
        this.infrastructureElement = infrastructureElement;
        this.category = category;
        this.status = status;
    }

    public String getInfrastructureElement() {
        return infrastructureElement;
    }

    public void setInfrastructureElement(String infrastructureElement) {
        this.infrastructureElement = infrastructureElement;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BenchmarkData{" +
                "id='" + getId() + '\'' +
                ", type='" + getType() + '\'' +
                ", infrastructureElement='" + infrastructureElement + '\'' +
                ", category='" + category + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
