package pl.polsl.fitstat.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.polsl.fitstat.dtos.RecordDTO;
import pl.polsl.fitstat.services.RecordLogService;

@Controller
public class RecordLogController {

    private final RecordLogService service;

    public RecordLogController(RecordLogService service) {
        this.service = service;
    }


    @GetMapping("/records/{recordId}")
    public ResponseEntity<?> getRecordLogById(@PathVariable long recordId){
        return ResponseEntity.status(HttpStatus.OK).body(service.getRecordLogByIdAndMap(recordId));
    }

    @GetMapping("/records/current")
    public ResponseEntity<?> getAllCurrentUsersCurrentRecords(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllCurrentUsersCurrentRecords());
    }

    @GetMapping("/records/units")
    public ResponseEntity<?> getUnits(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getUnits());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/records/all")
    public ResponseEntity<?> getAllRecordLogs(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllRecordLogs());
    }

    @GetMapping("/records")
    public ResponseEntity<?> getAllCurrentUsersRecordLogs(){
        return null;// ResponseEntity.status(HttpStatus.OK).body(service.getAllCurrentUsersRecordLogs());
    }

    @GetMapping("/activities/{activityId}/records")
    public ResponseEntity<?> getCurrentUsersRecordLogsByActivityId(@PathVariable long activityId){
        return ResponseEntity.status(HttpStatus.OK).body(service.getCurrentUsersRecordLogsByActivityId(activityId));
    }

    @PostMapping("/activities/{activityId}/records")
    public ResponseEntity<?> addRecordLogToCurrentUser(@PathVariable long activityId, @RequestBody RecordDTO recordDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addRecordLogToCurrentUser(activityId,recordDTO));
    }

    @PutMapping("/records/{recordId}")
    public ResponseEntity<?> updateRecordLogById(@PathVariable long recordId, @RequestBody RecordDTO recordDTO){
        return ResponseEntity.status(HttpStatus.OK).body(service.updateRecordLogById(recordId, recordDTO));
    }

    @DeleteMapping("/records/{recordId}")
    public ResponseEntity<?> deleteRecordLogById(@PathVariable long recordId){
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteRecordLogById(recordId));
    }

}
