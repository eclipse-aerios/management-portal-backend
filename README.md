# Management Portal Backend
Backend of the Eclipse aeriOS Management Portal developed using Spring Framework 6 and Spring Boot 3. Although it can work as an independent REST API, it is intended to use as the backend of the [Eclipse aeriOS Management Portal frontend](https://github.com/eclipse-aerios/management-portal-frontend).

## Developer guide

Minimum prerequirements for the local development machine:
- [JDK 21](https://www.oracle.com/es/java/technologies/downloads/#java21)
- [Maven 3.6](https://maven.apache.org)

The recommended IDE to develop this component is [IntelliJ IDEA](https://www.jetbrains.com/idea/) but other alternatives such as Eclipse Java IDE and VSCode can be used as well.

Finally, the Spring application can be configured through the modification of the *src/main/resources/application.properties* file. The application will run by default in the port 8080 of the local machine:

```properties
# Logging
logging.level.eu.aeriosproject=${LOG_LEVEL:INFO}
logging.level.org.springframework.ldap=${LDAP_LOG_LEVEL:INFO}
logging.level.org.springframework.security=${SECURITY_LOG_LEVEL:INFO}

# aeriOS configuration
aerios.entrypoint-balancer=${ENTRYPOINT_BALANCER_URL:http://entrypoint-balancer.default.svc.cluster.local:8080}
aerios.hlo-fe=${HLO_FE_URL:http://hlo-fe-service.default.svc.cluster.local:8081}
aerios.orion-ld=${ORION_URL:http://orion-ld-broker.default.svc.cluster.local:1026/ngsi-ld/v1/entities}

# External LDAP directory configuration
spring.ldap.urls=${LDAP_URL:ldap://openldap.default.svc.cluster.local:389}
spring.ldap.username=${LDAP_USERNAME:cn=admin,dc=example,dc=org}
spring.ldap.password=${LDAP_PASSWORD:Your@Passw0rd}
```

## Packaging

### Package into a JAR file
This application can be packaged into a Java JAR file using Maven. This JAR file will be created inside the *target* folder named as *management-portal-backend-{POM-application-version}.jar*, for instance, *management-portal-backend-1.2.2.jar*. The application version is specified in the line 13 of the *pom.xml* file.

```bash
mvn clean compile package
```

### Build a Docker image 
A Dockerfile is provided to build container images. The prerequirement is to package the backend into a JAR file.

```bash
docker build -t <eclipse-aerios-container-registry>/management-portal/backend:<app-version> --build-arg APP_VERSION=<app-version> .
```

For instance:
```bash
docker build -t <eclipse-aerios-container-registry>/management-portal/backend:1.2.2 --build-arg APP_VERSION=1.2.2 .
```

## Configuration
The backend can be configured using the default *application.properties* file or environment variables. These are the configurable parameters:

- **LOG_LEVEL**: logging level of the application (ERROR, WARN, INFO, DEBUG, or TRACE).
- **SECURITY_LOG_LEVEL**: logging level of security modules of the application (it includes the Spring Security 6 framework).
- **LDAP_LOG_LEVEL**: logging level of LDAP modules of the application (it includes the Spring Data LDAP 3 framework).
- **ORION_URL**: URL of the Orion-LD instance (including the path to its *entities* endpoint) of the entrypoint domain.
- **HLO_FE_URL**: URL of the HLO-FE API of the entrypoint domain. **It will be replaced by the URL of the Entrypoint Balancer**.
- **KEYCLOAK_URL**: URL of the Keycloak instance of the entrypoint domain.
  **KEYCLOAK_REALM**: realm of the Keycloak instance of the entrypoint domain.
- **LDAP_URL**: URL of the LDAP instance of the entrypoint domain.
- **LDAP_USERNAME**: username of the LDAP instance of the entrypoint domain.
- **LDAP_PASSWORD**: password of the LDAP instance of the entrypoint domain.


## How to run
The backend of the Management Portal an work as an independent REST API, but it has been designed to be deployed along with the frontend of the portal.

### As an standalone REST API

This backend component has been packaged into a Docker image, so it can be run using the provided Docker compose file:

```bash
docker compose up -d
```

and the provided K8s manifest file:

```bash
kubectl apply -f k8s-deployment.yaml
```

### As the Management Portal as a whole

A Helm chart and a Docker compose file is available to deploy the Management portal as a whole (frontend, backend and entrypoint balancer). This content can be found in the GitHub repository of the [Frontend](https://github.com/eclipse-aerios/management-portal-frontend).


## Integration with other aeriOS components

This components are required to run this backend:

- Orion-LD
- Keycloak
- OpenLDAP
- HLO-FE
- Entrypoint balancer (only if enabled)
