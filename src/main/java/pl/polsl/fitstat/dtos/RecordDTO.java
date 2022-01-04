package pl.polsl.fitstat.dtos;

import pl.polsl.fitstat.models.ActivityEntity;
import pl.polsl.fitstat.models.RecordLogEntity;
import pl.polsl.fitstat.models.UserEntity;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import java.time.LocalDate;

public class RecordDTO {

    private Long id;

    private LocalDate recordDate;

    private Boolean isDeleted;

    private Boolean isHistoric;

    private Integer value;

    private Long unitId;

    private UnitDTO unit;

    private ActivityDTO activityEntity;

    private UserDTO userEntity;

    public RecordDTO() {
    }

    public RecordDTO(RecordLogEntity recordLogEntity) {
        this.id = recordLogEntity.getId();;
        this.recordDate = recordLogEntity.getRecordDate();
        this.isDeleted = recordLogEntity.isDeleted();
        this.isHistoric = recordLogEntity.isHistoric();
        this.value = recordLogEntity.getValue();
        this.unit = new UnitDTO(recordLogEntity.getUnit());
        this.activityEntity = new ActivityDTO(recordLogEntity.getActivityEntity());
//        this.userEntity = new UserDTO(recordLogEntity.getUserEntity());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getHistoric() {
        return isHistoric;
    }

    public void setHistoric(Boolean historic) {
        isHistoric = historic;
    }

    public UnitDTO getUnit() {
        return unit;
    }

    public void setUnit(UnitDTO unit) {
        this.unit = unit;
    }

    public ActivityDTO getActivityEntity() {
        return activityEntity;
    }

    public void setActivityEntity(ActivityDTO activityEntity) {
        this.activityEntity = activityEntity;
    }

    public UserDTO getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserDTO userEntity) {
        this.userEntity = userEntity;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }
}
