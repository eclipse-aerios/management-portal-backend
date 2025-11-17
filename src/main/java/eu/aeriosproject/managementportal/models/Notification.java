package eu.aeriosproject.managementportal.models;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Notification {
    private String id;
    private String type;
    private String category;
    private String description;
    private String dateIssued;
    private String alertSource;
    private NotificationSeverity severity;

    @JsonCreator
    public Notification(String id, String type, String category, String description, String dateIssued, String alertSource, NotificationSeverity severity) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.description = description;
        this.dateIssued = dateIssued;
        this.alertSource = alertSource;
        this.severity = severity;
    }

    public Notification(){
    }

    public String getId() {
        return id;
    }

    public String getType() { return type; }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateIssued() {
        return dateIssued;
    }

    public String getAlertSource() {
        return alertSource;
    }

    public void setAlertSource(String alertSource) {
        this.alertSource = alertSource;
    }

    public NotificationSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(NotificationSeverity severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        return "Notification sent with " + this.getSeverity() + " priority by " + this.alertSource + " at " + this.dateIssued + " with the following message: " + this.description;
    }
}
