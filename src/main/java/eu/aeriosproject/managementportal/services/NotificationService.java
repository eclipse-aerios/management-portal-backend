package eu.aeriosproject.managementportal.services;

import eu.aeriosproject.managementportal.models.NewNotification;
import eu.aeriosproject.managementportal.models.NgsiLdSubscriptionNotification;
import eu.aeriosproject.managementportal.models.Notification;

import java.util.List;

public interface NotificationService {

    List<Notification> listNotifications();
    Notification getNotification(String notificationId);
    NewNotification createNotificationFromBenchmarkSubscription(NgsiLdSubscriptionNotification notification);
    void createNotificationInOrion(NewNotification notification);
    void deleteNotification(String notificationId);
    void clearNotifications();
}
