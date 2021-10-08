package pl.polsl.fitstat.security;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import pl.polsl.fitstat.models.UserEntity;

import java.util.Arrays;

@Service
public class KeycloakService {

    private final RealmClass realm;

    public KeycloakService(RealmClass realm) {
        this.realm = realm;
    }

    public UserEntity createUser(){

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue("test123");
        UserRepresentation user = new UserRepresentation();
        user.setUsername("testuser");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setCredentials(Arrays.asList(credential));
        user.setEnabled(true);

        realm.getRealm().users().create(user);

        return new UserEntity();
    }
}


