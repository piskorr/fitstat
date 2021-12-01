package pl.polsl.fitstat.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.polsl.fitstat.dtos.ActivityEntryDTO;
import pl.polsl.fitstat.services.ActivityEntryService;

@Controller
public class ActivityEntryController {

    private final ActivityEntryService service;

    public ActivityEntryController(ActivityEntryService service) {
        this.service = service;
    }

    @GetMapping("/entries")
    public ResponseEntity<?> getAllCurrentUsersActivityEntries(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllCurrentUsersActivityEntries());
    }

    @GetMapping("/entries/{entryId}")
    public ResponseEntity<?> getActivityEntryById(@PathVariable long entryId){
        return ResponseEntity.status(HttpStatus.OK).body(service.getActivityEntryById(entryId));
    }

    @GetMapping("activities/{activityId}/entries")
    public ResponseEntity<?> getAllCurrentUsersActivityEntriesByActivityId(@PathVariable long activityId){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllCurrentUsersActivityEntriesByActivityId(activityId));
    }

    @GetMapping("activities/{activityId}/entries/all")
    public ResponseEntity<?> getAllActivityEntriesByActivityId(@PathVariable long activityId){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllActivityEntriesByActivityId(activityId));
    }

    @PostMapping("activities/{activityId}/entries")
    public ResponseEntity<?> addActivityEntryToCurrentUser(@PathVariable long activityId, @RequestBody ActivityEntryDTO activityEntryDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addActivityEntryToCurrentUser(activityId, activityEntryDTO));
    }

    @PatchMapping("entries/{entryId}")
    public ResponseEntity<?> updateActivityEntryById(@PathVariable long entryId, @RequestBody ActivityEntryDTO activityEntryDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.updateActivityEntryById(entryId, activityEntryDTO));
    }

    @DeleteMapping("entries/{entryId}")
    public ResponseEntity<?> deleteActivityEntryById(@PathVariable long entryId){
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteActivityEntryById(entryId));
    }
}
