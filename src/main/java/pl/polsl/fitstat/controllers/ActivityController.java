package pl.polsl.fitstat.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.polsl.fitstat.dtos.ActivityDTO;
import pl.polsl.fitstat.services.ActivityService;

import javax.validation.Valid;

@Controller
public class ActivityController {

    private final ActivityService service;

    public ActivityController(ActivityService service) {
        this.service = service;
    }

    @GetMapping("/activities/all")
    public ResponseEntity<?> getAllActivities() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllActivities());
    }

    @GetMapping("/activities/{id}")
    public ResponseEntity<?> getActivityById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActivityById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/activities")
    public ResponseEntity<?> addNewActivity(@RequestBody @Valid ActivityDTO activityDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addNewActivity(activityDTO));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/activities/{id}")
    public ResponseEntity<?> updateActivityById(@PathVariable long id, @RequestBody ActivityDTO activityDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateActivityById(id, activityDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/activities/{id}")
    public ResponseEntity<?> deleteActivityById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteActivityById(id));
    }

}
