package pl.polsl.fitstat.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.polsl.fitstat.dtos.ActivityEntryDTO;
import pl.polsl.fitstat.services.ActivityEntryService;

import java.util.Map;

@Controller
public class ActivityEntryController {

    private final ActivityEntryService service;

    public ActivityEntryController(ActivityEntryService service) {
        this.service = service;
    }

    @GetMapping("/entries")
    public ResponseEntity<?> getAllCurrentUsersActivityEntries() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllCurrentUsersActivityEntries());
    }

    @GetMapping("/entries/today")
    public ResponseEntity<?> getCurrentUsersTodaysActivityEntries() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getCurrentUsersTodaysActivityEntries());
    }

    @GetMapping("/entries/calories")
    public ResponseEntity<?> getCurrentUsersWeeklyCalories() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getCurrentUsersWeeklyCalories());
    }

    @GetMapping("/entries/{entryId}")
    public ResponseEntity<?> getActivityEntryById(@PathVariable long entryId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActivityEntryByIdAndMap(entryId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("activities/{activityId}/entries/all")
    public ResponseEntity<?> getAllActivityEntriesByActivityId(@PathVariable long activityId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllActivityEntriesByActivityId(activityId));
    }

    @GetMapping("activities/{activityId}/entries")
    public ResponseEntity<?> getAllCurrentUsersActivityEntriesByActivityId(@PathVariable long activityId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllCurrentUsersActivityEntriesByActivityId(activityId));
    }

    @PostMapping("activities/{activityId}/entries")
    public ResponseEntity<?> addActivityEntryToCurrentUser(@PathVariable long activityId, @RequestBody ActivityEntryDTO activityEntryDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addActivityEntryToCurrentUser(activityId, activityEntryDTO));
    }

    @PatchMapping("entries/{entryId}")
    public ResponseEntity<?> updateActivityEntryById(@PathVariable long entryId, @RequestBody ActivityEntryDTO activityEntryDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.updateActivityEntryById(entryId, activityEntryDTO));
    }

    @DeleteMapping("entries/{entryId}")
    public ResponseEntity<?> deleteActivityEntryById(@PathVariable long entryId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteActivityEntryById(entryId));
    }
}
