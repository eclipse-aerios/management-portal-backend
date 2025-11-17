package eu.aeriosproject.managementportal.configuration;

import eu.aeriosproject.managementportal.configuration.security.AeriosClientInterceptor;
import eu.aeriosproject.managementportal.configuration.security.AeriosDataClientInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

@Configuration
public class AeriosRestTemplate {
    @Bean
    public RestTemplate unsafeRestTemplate() throws Exception {
        // Trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                }
        };

        // Create SSL context with our trust manager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new AeriosClientInterceptor());
        return restTemplate;
    }

    @Bean
    public RestTemplate hloRestTemplate() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new AeriosClientInterceptor());
        return restTemplate;
    }

    @Bean
    public RestTemplate orionRestTemplate() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new AeriosClientInterceptor());
        return restTemplate;
    }

    @Bean
    public RestTemplate orionDataFabricRestTemplate() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new AeriosDataClientInterceptor());
        return restTemplate;
    }

}
