package pl.polsl.fitstat.services;

import org.springframework.stereotype.Service;
import pl.polsl.fitstat.dtos.RecordDTO;
import pl.polsl.fitstat.dtos.UnitDTO;
import pl.polsl.fitstat.enums.UnitEnum;
import pl.polsl.fitstat.errors.ResourceNotFoundException;
import pl.polsl.fitstat.models.ActivityEntity;
import pl.polsl.fitstat.models.RecordLogEntity;
import pl.polsl.fitstat.models.UnitEntity;
import pl.polsl.fitstat.models.UserEntity;
import pl.polsl.fitstat.repositories.RecordLogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecordLogService {

    private final RecordLogRepository repository;
    private final UserService userService;
    private final ActivityService activityService;
    private final UnitService unitService;

    public RecordLogService(RecordLogRepository repository, UserService userService, ActivityService activityService, UnitService unitService) {
        this.repository = repository;
        this.userService = userService;
        this.activityService = activityService;
        this.unitService = unitService;
    }

    public RecordLogEntity getRecordLogById(long recordId) {
        return repository.findById(recordId)
                .filter(recordLogEntity -> !recordLogEntity.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Record with id: " + recordId + " does not exist!"));
    }

    public RecordDTO getRecordLogByIdAndMap(long recordId) {
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
        UserEntity user = userService.getCurrentUser();
        return repository.findAllByUserEntity_IdAndIsHistoricFalse(user.getId())
                .stream()
                .filter(recordLogEntity -> !recordLogEntity.isDeleted())
                .map(RecordDTO::new).collect(Collectors.toList());
    }

    public RecordDTO addRecordLogToCurrentUser(long activityId, RecordDTO recordDTO) {
        ActivityEntity activity = activityService.getActivityById(activityId);
        UserEntity user = userService.getCurrentUser();
        UnitEntity unit = unitService.getUnitById(recordDTO.getUnitId());
        RecordLogEntity newRecord = new RecordLogEntity(recordDTO, activity, user, unit);
        repository.findAllByUserEntity_IdAndUnit_IdAndActivityEntity_IdAndIsHistoricFalse(user.getId(), unit.getId(), activityId)
                .filter(recordLogEntity -> !recordLogEntity.isDeleted())
                .ifPresentOrElse(currentRecord -> {
                            if (unit.getUnit().equals(UnitEnum.second.toString())) {
                                if (currentRecord.getValue() > newRecord.getValue()) {
                                    currentRecord.setHistoric(true);
                                    newRecord.setHistoric(false);
                                    repository.save(currentRecord);
                                }
                            } else {
                                if (currentRecord.getValue() < newRecord.getValue()) {
                                    currentRecord.setHistoric(true);
                                    newRecord.setHistoric(false);
                                    repository.save(currentRecord);
                                }
                            }
                        },
                        () -> newRecord.setHistoric(false));

        repository.save(newRecord);
        return new RecordDTO(newRecord);
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

    public List<UnitDTO> getUnits() {
        return new ArrayList<>(unitService.getAllUnits());
    }
}
