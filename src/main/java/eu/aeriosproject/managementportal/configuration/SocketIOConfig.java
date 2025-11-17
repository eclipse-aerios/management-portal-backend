package eu.aeriosproject.managementportal.configuration;

import com.corundumstudio.socketio.AuthorizationResult;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SocketIOConfig {
    private final String keycloakFullUrl;
    private final SocketIOServer server;
    Logger logger = LoggerFactory.getLogger(SocketIOConfig.class);

    public SocketIOConfig(@Value("${KEYCLOAK_URL:https://keycloak.aerios-project.eu}") String keycloakUrl, @Value("${KEYCLOAK_REALM:keycloack-openldap}") String keycloakRealm, String keycloakRealmPath, String keycloakTokenValidationPath) {
        // SocketIO configuration
        Configuration config = new Configuration();
        config.setHostname("0.0.0.0");
        config.setPort(8082);
        config.setContext("/notifications");
        // Set SocketIO token-based authentication
        config.setAuthorizationListener((HandshakeData handshakeData) -> {
            String token = handshakeData.getSingleUrlParam("token");
            return new AuthorizationResult(validateToken(token));
        });
        this.server = new SocketIOServer(config);
        // Set Keycloak token validation URL
        this.keycloakFullUrl = keycloakUrl + keycloakRealmPath + keycloakRealm + keycloakTokenValidationPath;
    }

    private boolean validateToken(String token) {
        logger.info("Checking the validity of the token...");
        if (token == null || token.isEmpty()) {
            return false;
        }
        // Check the token validity in Keycloak
        RestTemplate keycloakClient = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Void> response = keycloakClient.exchange(this.keycloakFullUrl, HttpMethod.GET, request, Void.class);
        logger.debug("Token validation response code: {}", response.getStatusCode());
        return response.getStatusCode() != HttpStatus.UNAUTHORIZED;
    }

    @PostConstruct
    private void startServer() {
        server.start();
        server.addConnectListener((ConnectListener) client -> logger.info("Client connected: {}", client.getSessionId()));
        server.addDisconnectListener((DisconnectListener) client -> logger.info("Client disconnected: {}", client.getSessionId()));
    }

    @PreDestroy
    private void stopServer() {
        server.stop();
    }

    public void sendNotification(String event, Object message) {
        logger.debug("Sending notification through Socket.io channel");
        server.getBroadcastOperations().sendEvent(event, message);
    }
}
