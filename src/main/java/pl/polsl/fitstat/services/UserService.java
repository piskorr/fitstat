package pl.polsl.fitstat.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.polsl.fitstat.dtos.UserDTO;
import pl.polsl.fitstat.errors.CredentialsTakenException;
import pl.polsl.fitstat.errors.ResourceNotFoundException;
import pl.polsl.fitstat.models.RoleEntity;
import pl.polsl.fitstat.models.UserEntity;
import pl.polsl.fitstat.models.WeightHistoryEntity;
import pl.polsl.fitstat.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;
    private final KeycloakService keycloak;
    private final RoleService roleService;
    private final WeightHistoryService weightHistoryService;
    private final UsersChallengeService usersChallengeService;

    public UserService(UserRepository repository, KeycloakService keycloak, RoleService roleService, @Lazy WeightHistoryService weightHistoryService, @Lazy UsersChallengeService usersChallengeService) {
        this.repository = repository;
        this.keycloak = keycloak;
        this.roleService = roleService;
        this.weightHistoryService = weightHistoryService;
        this.usersChallengeService = usersChallengeService;
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

    public List<UserEntity> getAllUsers() {
        return repository.findAll()
                .stream()
                .filter(userEntity -> !userEntity.isDeleted())
                .collect(Collectors.toList());
    }

    public UserDTO createUser(UserDTO userDTO) {
        checkForExistingData(userDTO);
        keycloak.createUser(userDTO);
        keycloak.assignUserRole(userDTO);
        UserEntity newUser = new UserEntity(userDTO);
        RoleEntity role = roleService.getRoleByName(userDTO.getRole());
        newUser.setRole(role);
        repository.save(newUser);
        usersChallengeService.addChallengesForUser(newUser);
        return new UserDTO(newUser);
    }

    public void changePassword(String newPassword) {
        UserEntity currentUser = getCurrentUser();
        //currentUser.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        repository.save(currentUser);
    }

    public UserDTO changeCurrentUsersWeight(double newWeight){
        UserEntity currentUser = getCurrentUser();
        weightHistoryService.addNewWeightEntry(new WeightHistoryEntity(currentUser));
        currentUser.setWeight((float) newWeight);
        return new UserDTO(repository.save(currentUser));
    }

    public void checkPassword(String password) {
//        boolean checkPassword = new BCryptPasswordEncoder().matches(password, getCurrentUser().getPassword());
//        if (!checkPassword) {
//            throw new ForbiddenException("Wrong Password");
//        }
    }

    public UserDTO deleteUser(long userId) {
        UserEntity user = getUserById(userId);
        keycloak.removeUser(user.getUsername());
        user.setDeleted(true);
        repository.save(user);
        return new UserDTO(user);
    }

    private void checkForExistingData(UserDTO userDTO) {
        if (isUsernameTaken(userDTO.getUsername()))
            throw new CredentialsTakenException("Username is already taken!");

        if (isEmailTaken(userDTO.getEmail()))
            throw new CredentialsTakenException("Email is already taken!");
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
