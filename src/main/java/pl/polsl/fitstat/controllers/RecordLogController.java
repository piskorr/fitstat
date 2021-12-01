package pl.polsl.fitstat.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import pl.polsl.fitstat.dtos.RecordDTO;
import pl.polsl.fitstat.services.RecordLogService;

@Controller
public class RecordLogController {

    private final RecordLogService service;

    public RecordLogController(RecordLogService service) {
        this.service = service;
    }


    public ResponseEntity<?> getRecordLogById(@PathVariable long recordId){
        return ResponseEntity.status(HttpStatus.OK).body(service.getRecordLogByIdAndMap(recordId));
    }

    public ResponseEntity<?> getAllRecordLogs(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllRecordLogs());
    }

    public ResponseEntity<?> getCurrentUsersRecordLogsByActivityId(@PathVariable long activityId){
        return ResponseEntity.status(HttpStatus.OK).body(service.getCurrentUsersRecordLogsByActivityId(activityId));
    }

    public ResponseEntity<?> addRecordLogToCurrentUser(@PathVariable long activityId, @RequestBody RecordDTO recordDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addRecordLogToCurrentUser(activityId,recordDTO));
    }

    public ResponseEntity<?> updateRecordLogById(@PathVariable long recordId, @RequestBody RecordDTO recordDTO){
        return ResponseEntity.status(HttpStatus.OK).body(service.updateRecordLogById(recordId, recordDTO));
    }

    public ResponseEntity<?> deleteRecordLogById(@PathVariable long recordId){
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteRecordLogById(recordId));
    }

}
