package pl.polsl.fitstat.services;

import org.springframework.stereotype.Service;
import pl.polsl.fitstat.dtos.RecordDTO;
import pl.polsl.fitstat.errors.ResourceNotFoundException;
import pl.polsl.fitstat.models.ActivityEntity;
import pl.polsl.fitstat.models.RecordLogEntity;
import pl.polsl.fitstat.repositories.RecordLogRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecordLogService {

    private final RecordLogRepository repository;
    private final UserService userService;
    private final ActivityService activityService;

    public RecordLogService(RecordLogRepository repository, UserService userService, ActivityService activityService) {
        this.repository = repository;
        this.userService = userService;
        this.activityService = activityService;
    }

    public RecordLogEntity getRecordLogById(long recordId) {
        return repository.findById(recordId)
                .filter(recordLogEntity -> !recordLogEntity.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Record with id: " + recordId + " does not exist!"));
    }

    public RecordDTO getRecordLogByIdAndMap(long recordId){
        RecordLogEntity recordLogEntity = getRecordLogById(recordId);
        userService.checkRightsToResource(recordLogEntity.getUserEntity().getId());
        return new RecordDTO(recordLogEntity);
    }

    public List<RecordDTO> getAllRecordLogs() {
        return repository.findAll()
                .stream()
                .filter(recordLogEntity -> !recordLogEntity.isDeleted())
                .map(RecordDTO::new)
                .collect(Collectors.toList());
    }

    public List<RecordDTO> getCurrentUsersRecordLogsByActivityId(long activityId) {
        return repository.findAllByActivityEntity_IdAndUserEntity_Id(activityId, userService.getCurrentUser().getId())
                .stream()
                .filter(recordLogEntity -> !recordLogEntity.isDeleted())
                .map(RecordDTO::new)
                .collect(Collectors.toList());
    }

    public List<RecordDTO> getAllCurrentUsersCurrentRecords() {
        return repository.findAllByUserEntity_IdAndIsHistoricFalse(userService.getCurrentUser().getId())
                .stream()
                .filter(recordLogEntity -> !recordLogEntity.isDeleted())
                .map(RecordDTO::new)
                .collect(Collectors.toList());
    }

    public RecordDTO addRecordLogToCurrentUser(long activityId, RecordDTO recordDTO) {
        ActivityEntity activity = activityService.getActivityById(activityId);
        Optional<RecordLogEntity> oldRecord = getCurrentRecordLogByActivityIdAndUserId(activityId, userService.getCurrentUser().getId());
        if (oldRecord.isPresent()) {
            oldRecord.get().setDeleted(true);
            repository.save(oldRecord.get());
        }
        RecordLogEntity recordLogEntity = new RecordLogEntity(recordDTO, activity, userService.getCurrentUser());
        repository.save(recordLogEntity);
        return new RecordDTO(recordLogEntity);
    }

    private Optional<RecordLogEntity> getCurrentRecordLogByActivityIdAndUserId(long activityId, long userId) {
        return repository.findByActivityEntity_IdAndUserEntity_IdAndIsHistoricFalseAndIsDeletedFalse(activityId, userId);
    }

    public RecordDTO updateRecordLogById(long recordId, RecordDTO recordDTO) {
        RecordLogEntity recordLogEntity = getRecordLogById(recordId);
        userService.checkRightsToResource(recordLogEntity.getUserEntity().getId());
        updateRecordLog(recordLogEntity, recordDTO);
        repository.save(recordLogEntity);
        return new RecordDTO(recordLogEntity);
    }

    private void updateRecordLog(RecordLogEntity recordLogEntity, RecordDTO recordDTO) {
        if (recordDTO.getRecordDate() != null) {
            recordLogEntity.setRecordDate(recordDTO.getRecordDate());
        }
        //TODO
    }

    public RecordDTO deleteRecordLogById(long recordId) {
        RecordLogEntity recordLogEntity = getRecordLogById(recordId);
        userService.checkRightsToResource(recordLogEntity.getUserEntity().getId());
        recordLogEntity.setDeleted(true);
        repository.save(recordLogEntity);
        return new RecordDTO(recordLogEntity);
    }

    public void deleteAllRecordLogsByUserId(long userId) {
        List<RecordLogEntity> recordLogEntityList = repository.findAllByUserEntity_Id(userId)
                .stream()
                .filter(recordLogEntity -> !recordLogEntity.isDeleted())
                .collect(Collectors.toList());

        recordLogEntityList.forEach(recordLogEntity -> recordLogEntity.setDeleted(true));
        repository.saveAll(recordLogEntityList);
    }

}
