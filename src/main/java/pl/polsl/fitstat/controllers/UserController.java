package pl.polsl.fitstat.controllers;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.polsl.fitstat.dtos.UserDTO;
import pl.polsl.fitstat.services.UserService;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.util.List;

@RestController
@RequestMapping("/public/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public UserDTO getUsersProfile() {
        return new UserDTO(userService.getCurrentUser());
    }

    @PostMapping("/profile/password")
    public UserDTO changeUsersPassword() {
        return new UserDTO(userService.getCurrentUser());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(id));
    }


}
