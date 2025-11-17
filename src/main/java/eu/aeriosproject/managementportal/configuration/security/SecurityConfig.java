package eu.aeriosproject.managementportal.configuration.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final String keycloakFullUrl;
    private final KeycloakJwtTokenConverter keycloakJwtTokenConverter;
    Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    public SecurityConfig(TokenConverterProperties properties, @Value("${KEYCLOAK_URL:https://keycloak.aerios-project.eu}") String keycloakUrl, @Value("${KEYCLOAK_REALM:keycloack-openldap}") String keycloakRealm, String keycloakRealmPath, String keycloakTokenCertsPath) {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        this.keycloakJwtTokenConverter = new KeycloakJwtTokenConverter(jwtGrantedAuthoritiesConverter, properties);
        // Set Keycloak token validation URL
        this.keycloakFullUrl = keycloakUrl + keycloakRealmPath + keycloakRealm + keycloakTokenCertsPath;
    }

    @Bean
    public SecurityFilterChain resourceServerFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.cors(Customizer.withDefaults());
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(AbstractHttpConfigurer::disable);
        List<String> corsAllowedMethods = new ArrayList<>(){{
            add("OPTIONS");
            add("GET");
            add("POST");
            add("PUT");
            add("DELETE");
        }};

        // TODO improve CORS config
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.applyPermitDefaultValues().setAllowedMethods(corsAllowedMethods);
//        httpSecurity.cors((corsConfigurer -> corsConfigurer.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())));
        httpSecurity.cors((corsConfigurer -> corsConfigurer.configurationSource(request -> corsConfiguration)));

        httpSecurity.authorizeHttpRequests(authorize -> authorize.requestMatchers("/ws/**").permitAll());
        httpSecurity.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        httpSecurity.oauth2ResourceServer(oauth2->oauth2.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(keycloakJwtTokenConverter)));
        httpSecurity.oauth2ResourceServer(oauth2->oauth2.jwt(jwtConfigurer -> jwtConfigurer.jwkSetUri(keycloakFullUrl)));
        httpSecurity.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();
    }
}
