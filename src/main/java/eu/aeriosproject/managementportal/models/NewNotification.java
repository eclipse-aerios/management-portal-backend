package eu.aeriosproject.managementportal.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.aeriosproject.managementportal.utils.RandomId;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class NewNotification {
    private final String id;
    private final String type;
    private String category;
    private String description;
    private final String dateIssued;
    private String alertSource;
    private NotificationSeverity severity;

    private final String NOTIFICATION_PREFIX = "urn:ngsi-ld:Alert:%s";
    private final String ENTITY_TYPE = "Alert";

    @JsonCreator
    public NewNotification(@JsonProperty(value = "id", required = false) String id, @JsonProperty(value = "type", required = false) String type, String category, String description, @JsonProperty(value = "dateIssued", required = false) String dateIssued, String alertSource, NotificationSeverity severity) {
        String randomId = UUID.randomUUID().toString();
        this.id = String.format(NOTIFICATION_PREFIX, randomId);
        this.type = ENTITY_TYPE;
        this.category = category;
        this.description = description;
        this.alertSource = alertSource;
        this.severity = severity;

        Instant now = Instant.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .withZone(ZoneId.of("UTC"));
        this.dateIssued = formatter.format(now);
    }

    public NewNotification(String category, String description, String dateIssued, String alertSource, NotificationSeverity severity) {
        String randomId = UUID.randomUUID().toString();
        this.id = String.format(NOTIFICATION_PREFIX, randomId);
        this.type = ENTITY_TYPE;
        this.category = category;
        this.description = description;
        this.dateIssued = dateIssued;
        this.alertSource = alertSource;
        this.severity = severity;
    }

    public NewNotification(String category, String description, String alertSource, NotificationSeverity severity) {
        String randomId = UUID.randomUUID().toString();
        this.id = String.format(NOTIFICATION_PREFIX, randomId);
        this.type = ENTITY_TYPE;
        this.category = category;
        this.description = description;
        this.alertSource = alertSource;
        this.severity = severity;

        Instant now = Instant.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .withZone(ZoneId.of("UTC"));
        this.dateIssued = formatter.format(now);
    }

    public NewNotification(){
        RandomId notificationId = new RandomId(8, ThreadLocalRandom.current());
        this.id = String.format(NOTIFICATION_PREFIX, notificationId.toString().split("@")[1]);
        this.type = ENTITY_TYPE;
        Instant now = Instant.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .withZone(ZoneId.of("UTC"));
        this.dateIssued = formatter.format(now);
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
