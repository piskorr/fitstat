package pl.polsl.fitstat.dtos;

import pl.polsl.fitstat.models.RecordLogEntity;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import java.time.LocalDate;

public class RecordDTO {

    private Long id;

    private LocalDate recordDate;

    @Min(value = 1, message = "Value of repetitions must be greater than 0")
    private Integer reps;

    @Min(value = 1, message = "Value of time must be greater than 0")
    private Integer time;

    @Min(value = 1, message = "Value of distance must be greater than 0")
    private Double distance;

    private Boolean isDeleted;

    private Boolean isHistoric;

    private String activityEntity;

    private String userEntity;

    public RecordDTO() {
    }

    public RecordDTO(RecordLogEntity recordLogEntity) {
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

    public String getActivityEntity() {
        return activityEntity;
    }

    public void setActivityEntity(String activityEntity) {
        this.activityEntity = activityEntity;
    }

    public String getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(String userEntity) {
        this.userEntity = userEntity;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Boolean getHistoric() {
        return isHistoric;
    }

    public void setHistoric(Boolean historic) {
        isHistoric = historic;
    }
}
