package pl.polsl.fitstat.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.polsl.fitstat.dtos.WeightEntryDTO;
import pl.polsl.fitstat.services.WeightHistoryService;

@Controller
public class WeightHistoryController {

    private final WeightHistoryService service;

    public WeightHistoryController(WeightHistoryService service) {
        this.service = service;
    }


    @GetMapping("/users/weight/{entryId}")
    public ResponseEntity<?> getWeightEntryById(@PathVariable long entryId){
        return ResponseEntity.status(HttpStatus.OK).body(service.getWeightEntryByIdAndMap(entryId));
    }

    @GetMapping("/users/weight")
    public ResponseEntity<?> getCurrentUsersWeightEntries(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getCurrentUsersWeightEntries());
    }

    @PostMapping("/users/weight")
    public ResponseEntity<?> addNewWeightEntryToCurrentUser(@RequestBody WeightEntryDTO weightEntryDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addNewWeightEntryToCurrentUser(weightEntryDTO));
    }

    @DeleteMapping("/users/weight/{entryId}")
    public ResponseEntity<?> deleteWeightEntryById(@PathVariable long entryId){
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteWeightEntryById(entryId));
    }
}
