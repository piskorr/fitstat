package pl.polsl.fitstat.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.polsl.fitstat.services.UsersChallengeService;

@Controller
public class UsersChallengeController {

    private final UsersChallengeService service;

    public UsersChallengeController(UsersChallengeService service) {
        this.service = service;
    }


    @GetMapping("users/challenges/{challengeId}")
    public ResponseEntity<?> getChallengeEntryById(@PathVariable long challengeId){
        return ResponseEntity.status(HttpStatus.OK).body(service.getUsersChallengeByIdAndMap(challengeId));
    }

    @GetMapping("users/challenges")
    public ResponseEntity<?> getCurrentUsersChallenges(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getCurrentUsersChallenges());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("users/challenges/{challengeId}")
    public ResponseEntity<?> deleteChallengeById(@PathVariable long challengeId){
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteChallengeById(challengeId));
    }

}
