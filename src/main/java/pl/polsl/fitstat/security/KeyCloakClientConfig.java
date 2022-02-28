package pl.polsl.fitstat.security;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;

@Configuration
public class KeyCloakClientConfig {

    @Value("${keycloak.credentials.secret}")
    private String secretKey;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.auth-server-url}")
    private String authUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Bean
    public Keycloak getInstance() {
        return KeycloakBuilder.builder()
                .grantType(CLIENT_CREDENTIALS)
                .serverUrl(authUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(secretKey)
                .resteasyClient(
                        new ResteasyClientBuilder()
                                .connectionPoolSize(10)
                                .build()
                )
                .build();
    }

    public String getClientId() {
        return clientId;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public String getRealm() {
        return realm;
    }
}