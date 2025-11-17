package eu.aeriosproject.managementportal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = NgsiLdBenchmarkNotificationDataItem.class, name = "Benchmark")
})
public class NgsiLdSubscriptionNotificationDataItem {
    private String id;
    @JsonProperty("type")
    private String type;

    public NgsiLdSubscriptionNotificationDataItem() {
    }

    public NgsiLdSubscriptionNotificationDataItem(@JsonProperty("id") String id, @JsonProperty("type") String type) {
        this.id = id;
        this.type = type;
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
}
