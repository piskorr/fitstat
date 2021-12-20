package pl.polsl.fitstat.services;

import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.polsl.fitstat.dtos.UserDTO;
import pl.polsl.fitstat.enums.RoleEnum;
import pl.polsl.fitstat.errors.WrongPasswordException;
import pl.polsl.fitstat.models.UserEntity;
import pl.polsl.fitstat.security.KeyCloakClientConfig;

import java.util.Collections;
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

    public void logoutUserByUserId(String userId) {
        keycloak
                .getInstance()
                .realm(keycloak.getRealm())
                .users()
                .get(userId).logout();
    }

    public UserResource getUserResource(String userId) {
        return keycloak
                .getInstance()
                .realm(keycloak.getRealm())
                .users()
                .get(userId);
    }

    public void removeUserResource(String userId) {
        keycloak
                .getInstance()
                .realm(keycloak.getRealm())
                .users()
                .get(userId)
                .remove();
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

    public void changePassword(MultiValueMap body, UserEntity user) {
        // z jakiegoś powodu white space jest wokół password
        // fix
        String password = body.get("new_password").toString();
        password = password.substring(1, password.length() - 1);

        checkUserPassword(body, user);
        updatePassword(getUserIdByUsername(user.getUsername()), password);
    }

    private void updatePassword(String userId, String password) {
        keycloak.getInstance()
                .realm(keycloak.getRealm())
                .users()
                .get(userId)
                .resetPassword(preparePasswordRepresentation(password));
    }

    private void checkUserPassword(MultiValueMap body, UserEntity user) throws WrongPasswordException {
        String response = null;
        String url = "http://localhost:8080/auth/realms/fitstat-realm/protocol/openid-connect/token";
        body.add("client_id", "client-fitstat");
        body.add("username", user.getUsername());
        body.add("grant_type", "password");
        RestTemplate restTemplate = new RestTemplate();

        try {
            response = restTemplate.postForObject(url, body, String.class);
        } catch (HttpClientErrorException e) {
            response = e.getMessage();
        }

        if (response.contains("Unauthorized")) {
            throw new WrongPasswordException("Password is incorrect!");
        }
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


    private List<RoleRepresentation> prepareRoleRepresentation(String role) {
        return List.of(
                keycloak
                        .getInstance()
                        .realm(keycloak.getRealm())
                        .roles()
                        .get("ROLE_" + role)
                        .toRepresentation());
    }

    public void assignUserRole(UserDTO user) {
        List<RoleRepresentation> role = prepareRoleRepresentation(RoleEnum.USER.toString());
        String userId = getUserIdByUsername(user.getUsername());
        UserResource userResource = getUserResource(userId);
        userResource.roles().realmLevel().add(role);
    }

    public void assignAdminRole(UserEntity userEntity) {
        List<RoleRepresentation> role = prepareRoleRepresentation(RoleEnum.ADMIN.toString());
        String userId = getUserIdByUsername(userEntity.getUsername());
        UserResource userResource = getUserResource(userId);
        userResource.roles().realmLevel().add(role);
    }

    public void disableUser(String username) {
        String userId = getUserIdByUsername(username);
        UserResource userResource = getUserResource(userId);
        UserRepresentation updatedUser = userResource.toRepresentation();
        updatedUser.setEnabled(false);
        userResource.update(updatedUser);
    }

    public void removeUser(String username) {
        String userId = getUserIdByUsername(username);
        removeUserResource(userId);
    }

}
