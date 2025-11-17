FROM eclipse-temurin:21-jdk-alpine
LABEL org.opencontainers.image.authors="aerios-project"
ARG APP_VERSION

RUN addgroup -S aeriosportal && adduser -S aeriosportal -G aeriosportal
USER aeriosportal

COPY target/management-portal-backend-${APP_VERSION}.jar management-portal-backend.jar
ENTRYPOINT ["java","-jar","/management-portal-backend.jar"]