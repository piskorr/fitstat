package pl.polsl.fitstat.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.polsl.fitstat.dtos.ChallengeDTO;
import pl.polsl.fitstat.services.ChallengeService;

import javax.validation.Valid;

@Controller
public class ChallengeController {

    private final ChallengeService service;

    public ChallengeController(ChallengeService service) {
        this.service = service;
    }

    @GetMapping("/challenges/{challengeId}")
    public ResponseEntity<?> getChallengeById(@PathVariable long challengeId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getChallengeByIdAndMap(challengeId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/challenges/all")
    public ResponseEntity<?> getAllChallenges() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllChallenges());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/challenges")
    public ResponseEntity<?> addNewChallenge(@RequestBody @Valid ChallengeDTO challengeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addNewChallenge(challengeDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/challenges/{challengeId}")
    public ResponseEntity<?> updateChallengeById(@PathVariable long challengeId, @RequestBody @Valid ChallengeDTO challengeDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateChallengeById(challengeId, challengeDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/challenges/{challengeId}")
    public ResponseEntity<?> deleteChallengeById(@PathVariable long challengeId) {
        service.deleteChallengeById(challengeId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
