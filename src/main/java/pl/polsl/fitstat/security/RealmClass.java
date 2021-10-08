package pl.polsl.fitstat.security;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class RealmClass {

    private final static String serverUrl = "http://localhost:8080/auth";
    private final static String username = "admin";
    private final static String password = "admin";
    private final static String realm = "fitstat-realm";
    private final static String clientId = "admin-cli";
    private final static String clientSecret = "8216d99d-8a30-4f51-9c92-e6cbe01a7364";

//    @Bean
//    public RealmResource keycloak() {
//        final Keycloak keycloak = Keycloak.getInstance(
//                serverUrl,
//                realm,
//                username,
//                password,
//                clientId,
//                clientSecret);
//        return keycloak.realm(realm);
//    }

    @Bean
    public Keycloak getInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .resteasyClient(
                        new ResteasyClientBuilder()
                                .connectionPoolSize(10).build()
                ).build();
    }

    @Bean
    public RealmResource getRealm() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .resteasyClient(
                        new ResteasyClientBuilder()
                                .connectionPoolSize(10).build()
                ).build().realm(realm);
    }

}
