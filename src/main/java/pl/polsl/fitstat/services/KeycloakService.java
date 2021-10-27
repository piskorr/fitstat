package pl.polsl.fitstat.services;

import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import pl.polsl.fitstat.dtos.UserDTO;
import pl.polsl.fitstat.security.KeyCloakClientConfig;

import java.util.List;

@Service
public class KeycloakService {

    private final KeyCloakClientConfig keycloak;


    public KeycloakService(KeyCloakClientConfig keycloak) {
        this.keycloak = keycloak;
    }

    public List<UserRepresentation> findAll() {
        return keycloak.getInstance()
                .realm(keycloak.getRealm())
                .users()
                .list();
    }

    public String getUserIdByUsername(String username) {
        return keycloak
                .getInstance()
                .realm(keycloak.getRealm())
                .users()
                .search(username)
                .get(0)
                .getId();
    }

    public String getUsernameByUserId(String userId) {
        return keycloak
                .getInstance()
                .realm(keycloak.getRealm())
                .users()
                .get(userId)
                .toRepresentation()
                .getUsername();
    }

    public UserResource getUserResource(String userId) {
        return keycloak
                .getInstance()
                .realm(keycloak.getRealm())
                .users()
                .get(userId);
    }

    public void createUser(UserDTO userModel) {
        CredentialRepresentation password = preparePasswordRepresentation(userModel.getPassword());
        UserRepresentation user = prepareUserRepresentation(userModel, password);
        keycloak
                .getInstance()
                .realm(keycloak.getRealm())
                .users()
                .create(user);
    }

    private UserRepresentation prepareUserRepresentation(UserDTO userModel, CredentialRepresentation password) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userModel.getUsername());
        user.setCredentials(List.of(password));
        user.setEnabled(true);
        return user;
    }

    private CredentialRepresentation preparePasswordRepresentation(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        return credentialRepresentation;
    }

    private List<RoleRepresentation> prepareRoleRepresentation(UserDTO user) {
        return List.of(
                keycloak
                        .getInstance()
                        .realm(keycloak.getRealm())
                        .roles()
                        .get(user.getRole())
                        .toRepresentation());
    }

    public void assignUserRole(UserDTO user) {
        List<RoleRepresentation> role = prepareRoleRepresentation(user);
        String userId = getUserIdByUsername(user.getUsername());
        UserResource usersResourceToAddRole = getUserResource(userId);
        usersResourceToAddRole.roles().realmLevel().add(role);
    }

    public void disableUser(String username) {
        String userId = getUserIdByUsername(username);
        UserResource userResource = getUserResource(userId);
        UserRepresentation updatedUser = userResource.toRepresentation();
        updatedUser.setEnabled(false);
        userResource.update(updatedUser);
    }


}
