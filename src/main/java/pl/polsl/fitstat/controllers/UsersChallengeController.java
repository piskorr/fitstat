package pl.polsl.fitstat.controllers;

import org.springframework.stereotype.Controller;
import pl.polsl.fitstat.services.UsersChallengeService;

@Controller
public class UsersChallengeController {

    private final UsersChallengeService service;

    public UsersChallengeController(UsersChallengeService service) {
        this.service = service;
    }
}
