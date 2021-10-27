package pl.polsl.fitstat.controllers;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.polsl.fitstat.dtos.UserDTO;
import pl.polsl.fitstat.services.UserService;

import javax.ws.rs.core.Response;
import java.util.List;

@RestController
@RequestMapping("/public/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/res")
    @PreAuthorize("hasRole('USER')")
    public String test1() {
        return "user string";
    }

    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDTO));
    }


}
