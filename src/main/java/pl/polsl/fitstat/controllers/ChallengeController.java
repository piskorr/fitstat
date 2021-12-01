package pl.polsl.fitstat.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import pl.polsl.fitstat.dtos.ChallengeDTO;
import pl.polsl.fitstat.services.ChallengeService;

import javax.validation.Valid;

@Controller
public class ChallengeController {

    private final ChallengeService service;

    public ChallengeController(ChallengeService service) {
        this.service = service;
    }

    public ResponseEntity<?> getChallengeById(@PathVariable long challengeId){
        return ResponseEntity.status(HttpStatus.OK).body(service.getChallengeByIdAndMap(challengeId));
    }

    public ResponseEntity<?> getAllChallenges(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllChallenges());
    }

    public ResponseEntity<?> addNewChallenge(@RequestBody @Valid ChallengeDTO challengeDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addNewChallenge(challengeDTO));
    }

    public ResponseEntity<?> deleteChallengeById(@PathVariable long challengeId){
        service.deleteChallengeById(challengeId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
