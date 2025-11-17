package eu.aeriosproject.managementportal.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.regex.Pattern;

@Configuration
@EnableWebMvc
public class GeneralConfiguration {
    @Autowired
    private Environment env;

    @Bean
    public String keycloakRealmPath() {
        return "/auth/realms/";
    }
    @Bean
    public String keycloakTokenValidationPath() {
        return "/protocol/openid-connect/userinfo";
    }
    @Bean
    public String keycloakTokenCertsPath() {
        return "/protocol/openid-connect/certs";
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public Pattern ldapEncryptPattern() {
        return Pattern.compile("^\\{(CRYPT|BCRYPT|SHA|MD5)}(.*)");
    }

//    @Bean
//    public AeriosClientInterceptor authHeaderInterceptor() {
//        return new AeriosClientInterceptor();
//    }
}
