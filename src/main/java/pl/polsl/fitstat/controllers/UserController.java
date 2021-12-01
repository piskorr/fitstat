package pl.polsl.fitstat.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.polsl.fitstat.dtos.UserDTO;
import pl.polsl.fitstat.services.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/profile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUsersProfile() {
        return ResponseEntity.status(HttpStatus.OK).body(new UserDTO(userService.getCurrentUser()));
    }

    @PostMapping("/users/profile/password")
    public ResponseEntity<?> changeUsersPassword(@RequestBody String new_password) {
        userService.changePassword(new_password);
        return ResponseEntity.status(HttpStatus.OK).body("done");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDTO));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(id));
    }


}
