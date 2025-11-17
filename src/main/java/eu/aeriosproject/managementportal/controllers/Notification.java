package eu.aeriosproject.managementportal.controllers;

import eu.aeriosproject.managementportal.configuration.SocketIOConfig;
import eu.aeriosproject.managementportal.models.NewNotification;
import eu.aeriosproject.managementportal.models.NgsiLdSubscriptionNotification;
import eu.aeriosproject.managementportal.services.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portal-backend/notifications")
public class Notification {
    Logger logger = LoggerFactory.getLogger(Notification.class);
    @Autowired
    private NotificationService notificationService;
    private final SimpMessagingTemplate messagingTemplate;
    private final SocketIOConfig socketIOService;

    public Notification(SimpMessagingTemplate messagingTemplate, SocketIOConfig socketIOService) {
        this.messagingTemplate = messagingTemplate;
        this.socketIOService = socketIOService;
    }

    @PostMapping(value = {"", "/"})
    public ResponseEntity<Void> createNotification(@RequestBody NewNotification notification){
        logger.info(notification.toString());
        // Create notification entity in Orion
        notificationService.createNotificationInOrion(notification);
        // Send the notification via regular websocket
//        messagingTemplate.convertAndSend("/topic/notifications", notification);
        // or via socket.io
        socketIOService.sendNotification("notification", notification);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = {"/benchmark", "/benchmark/"})
    public ResponseEntity<Void> createBenchmarkNotification(@RequestBody NgsiLdSubscriptionNotification ngsiLdNotification){
        // Parse the notification from the NGSI-LD subscription and create a portal notification
        logger.debug(ngsiLdNotification.toString());
        NewNotification notification = notificationService.createNotificationFromBenchmarkSubscription(ngsiLdNotification);
        // Create notification entity in Orion
        notificationService.createNotificationInOrion(notification);
        // Send the notification via regular websocket
//        messagingTemplate.convertAndSend("/topic/notifications", notification);
        // or via socket.io
        socketIOService.sendNotification("notification", notification);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = {"", "/"})
    public List<eu.aeriosproject.managementportal.models.Notification> listNotifications() {
        return notificationService.listNotifications();
    }

    @GetMapping(value = "/{notificationId}")
    public eu.aeriosproject.managementportal.models.Notification getNotification(@PathVariable String notificationId){
        return notificationService.getNotification(notificationId);
    }

    @DeleteMapping(value = "/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable String notificationId){
        notificationService.deleteNotification(notificationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/clear")
    public ResponseEntity<Void> clearNotifications(){
        notificationService.clearNotifications();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
