package eu.aeriosproject.managementportal.configuration.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

@Component
public class AeriosClientInterceptor implements ClientHttpRequestInterceptor {
    Logger logger = LoggerFactory.getLogger(AeriosClientInterceptor.class);
    @Override
    @NonNull
    public ClientHttpResponse intercept(HttpRequest request, @NonNull byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logger.debug("Adding needed aeriOS headers to the request: aerOS and Authorization");
        String authorizationHeader = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        logger.debug(authorizationHeader);
        request.getHeaders().add("Authorization", authorizationHeader);
        request.getHeaders().add("aerOS", "true");

        // TODO check auth value
        return execution.execute(request, body);
    }
}
