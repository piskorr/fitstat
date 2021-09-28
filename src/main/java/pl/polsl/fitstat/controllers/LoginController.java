package pl.polsl.fitstat.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.fitstat.models.UserEntity;
import pl.polsl.fitstat.services.UserService;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final UserService service;

    public LoginController(UserService service) {
        this.service = service;
    }

    @PostMapping
    UserEntity register(@RequestParam String email, @RequestParam String password) {
        return service.register(email, password);
    }

    @PostMapping("/login")
    UserDetails login() {
        return service.loadUserByUsername("username");
    }

}
