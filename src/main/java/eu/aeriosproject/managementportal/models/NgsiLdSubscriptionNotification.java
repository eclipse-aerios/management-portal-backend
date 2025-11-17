package eu.aeriosproject.managementportal.models;

import java.util.List;

public class NgsiLdSubscriptionNotification {
    private String id;
    private String type;
    private String subscriptionId;
    private String notifiedAt;
    private List<NgsiLdSubscriptionNotificationDataItem> data;

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

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getNotifiedAt() {
        return notifiedAt;
    }

    public void setNotifiedAt(String notifiedAt) {
        this.notifiedAt = notifiedAt;
    }

    public List<NgsiLdSubscriptionNotificationDataItem> getData() {
        return data;
    }

    public void setData(List<NgsiLdSubscriptionNotificationDataItem> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", subscriptionId='" + subscriptionId + '\'' +
                ", notifiedAt='" + notifiedAt + '\'' +
                ", data=" + data +
                '}';
    }
}
