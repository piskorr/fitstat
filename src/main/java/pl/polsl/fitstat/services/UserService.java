package pl.polsl.fitstat.services;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.polsl.fitstat.dtos.UserDTO;
import pl.polsl.fitstat.errors.CredentialsTakenException;
import pl.polsl.fitstat.errors.ResourceNotFoundException;
import pl.polsl.fitstat.models.UserEntity;
import pl.polsl.fitstat.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final KeycloakService keycloak;

    public UserService(UserRepository repository, KeycloakService keycloak) {
        this.repository = repository;
        this.keycloak = keycloak;
    }

    public UserEntity getUserById(long userId) {
        return repository.findById(userId)
                .filter(userEntity -> !userEntity.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + userId + " does not exist!"));
    }

    public UserEntity getUserByUsername(String username) {
        return repository.findByUsername(username)
                .filter(userEntity -> !userEntity.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("User with username: " + username + " does not exist!"));
    }

    public UserEntity getUserByEmail(String email) {
        return repository.findByEmail(email)
                .filter((userEntity -> !userEntity.isDeleted()))
                .orElseThrow(() -> new ResourceNotFoundException("User with email: " + email + " does not exist!"));
    }

    public UserEntity getCurrentUser() {
        String username = null;
        Optional<Authentication> auth = Optional.ofNullable(SecurityContextHolder.getContext()
                .getAuthentication());

        if (auth.isPresent())
            username = keycloak.getUsernameByUserId(auth.get().getName());

        return getUserByUsername(username);
    }

    public UserDTO createUser(UserDTO userDTO) {
        validateUserData(userDTO);
        keycloak.createUser(userDTO);
        keycloak.assignUserRole(userDTO);
        UserEntity newUser = new UserEntity(userDTO);
        repository.save(newUser);
        return new UserDTO(newUser);
    }

    public UserDTO deleteUser(long userId) {
        UserEntity user = getUserById(userId);
        keycloak.disableUser(user.getUsername());
        user.setDeleted(true);
        repository.save(user);
        return new UserDTO(user);
    }

    private void validateUserData(UserDTO userDTO) {
        if(isUsernameTaken(userDTO.getUsername()))
            throw new CredentialsTakenException("Username is already taken!");

        if(isEmailTaken(userDTO.getEmail()))
            throw new CredentialsTakenException("Email is already taken!");

        if (!EmailValidator.getInstance().isValid(userDTO.getEmail()))
            throw new IllegalArgumentException("Email format is not correct!");
    }

    private boolean isEmailTaken(String email) {
        return repository.findByEmail(email)
                .filter(userEntity -> !userEntity.isDeleted())
                .isPresent();
    }

    private boolean isUsernameTaken(String username) {
        return repository.findByUsername(username)
                .filter(userEntity -> !userEntity.isDeleted())
                .isPresent();
    }
}
