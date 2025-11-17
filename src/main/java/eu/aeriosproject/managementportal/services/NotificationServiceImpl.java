package eu.aeriosproject.managementportal.services;

import eu.aeriosproject.managementportal.models.*;
import eu.aeriosproject.managementportal.utils.NgsildConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{
    Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
    @Value("${aerios.orion-ld}")
    private String ORION_URL;
    private final String ENTITY_TYPE = "Alert";

    private final RestTemplate orionClient = new RestTemplate();

    @Override
    public List<Notification> listNotifications() {
        ResponseEntity<Notification[]> response = orionClient
                .getForEntity(ORION_URL+"?type="+ENTITY_TYPE+"&format=simplified&local=true", Notification[].class);
        return Arrays.asList(response.getBody());
    }

    @Override
    public Notification getNotification(String notificationId) {
        ResponseEntity<Notification> response = orionClient
                .getForEntity(ORION_URL+"/{notificationId}?local=true&format=simplified", Notification.class, notificationId);
        return response.getBody();
    }

    @Override
    public NewNotification createNotificationFromBenchmarkSubscription(NgsiLdSubscriptionNotification notification) {
        List<NgsiLdBenchmarkNotificationDataItem> benchmarkDataList = notification.getData().stream()
                .filter(NgsiLdBenchmarkNotificationDataItem.class::isInstance) // Filter only BenchmarkData instances
                .map(NgsiLdBenchmarkNotificationDataItem.class::cast) // Cast them to BenchmarkData
                .toList();
        if (benchmarkDataList.isEmpty()) throw new ArrayIndexOutOfBoundsException();
        else {
            NgsiLdBenchmarkNotificationDataItem notificationData = benchmarkDataList.getFirst();
            logger.debug(notificationData.toString());
            String category = notificationData.getType() + "-" + notificationData.getCategory();
            String alertSource = NgsildConverter.trimId(notificationData.getInfrastructureElement());
            String description = getBenchmarkNotificationDescription(notificationData, alertSource);
            return new NewNotification(category, description, notification.getNotifiedAt(), alertSource, NotificationSeverity.informational);
        }
    }

    private static String getBenchmarkNotificationDescription(NgsiLdBenchmarkNotificationDataItem notificationData, String alertSource) {
        String description;
        if (notificationData.getStatus().equals("running"))
            description = String.format("Starting a benchmark execution in %s", alertSource);
        else if (notificationData.getStatus().equals("finished"))
            description =  String.format("Benchmark execution in %s finished successfully", alertSource);
        else
            description = String.format("Benchmark execution in %s finished with '%s' result", alertSource, notificationData.getStatus());
        return description;
    }

    @Override
    public void createNotificationInOrion(NewNotification notification) {
        orionClient.postForEntity(ORION_URL+"?local=true", notification, Void.class);
    }

    @Override
    public void deleteNotification(String notificationId) {
        orionClient.delete(ORION_URL+"/{notificationId}?local=true", notificationId);
    }

    @Override
    public void clearNotifications() {
        orionClient.delete(ORION_URL+"?local=true&type={entityType}", ENTITY_TYPE);
    }

}
