package pl.polsl.fitstat.dtos;

import pl.polsl.fitstat.models.ActivityEntryEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ActivityEntryDTO {

    private Long id;

    private LocalDateTime activityDate;

    @NotNull(message = "activity duration time cannot be null")
    @Min(value = 1, message = "activity duration time cannot be a negative or zero value")
    private Integer activityDuration;

    @NotNull(message = "calories burned value cannot be null")
    @Min(value = 1, message = "calories burned value cannot be a negative or zero value")
    private Integer caloriesBurned;

    private Long userEntity;

    private Long activityEntity;

    public ActivityEntryDTO() {
    }

    public ActivityEntryDTO(Long id, LocalDateTime activityDate, Integer activityDuration, Integer caloriesBurned, Long userEntity, Long activityEntity) {
        this.id = id;
        this.activityDate = activityDate;
        this.activityDuration = activityDuration;
        this.caloriesBurned = caloriesBurned;
        this.userEntity = userEntity;
        this.activityEntity = activityEntity;
    }

    public ActivityEntryDTO(ActivityEntryEntity activityEntry) {
        this.id = activityEntry.getId();
        this.activityDate = activityEntry.getActivityDate();
        this.activityDuration = activityEntry.getActivityDuration();
        this.caloriesBurned = activityEntry.getCaloriesBurned();
        this.userEntity = activityEntry.getUserEntity().getId();
        this.activityEntity = activityEntry.getActivityEntity().getId();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDateTime activityDate) {
        this.activityDate = activityDate;
    }

    public Integer getActivityDuration() {
        return activityDuration;
    }

    public void setActivityDuration(Integer activityDuration) {
        this.activityDuration = activityDuration;
    }

    public Integer getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(Integer caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public Long getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(Long userEntity) {
        this.userEntity = userEntity;
    }

    public Long getActivityEntity() {
        return activityEntity;
    }

    public void setActivityEntity(Long activityEntity) {
        this.activityEntity = activityEntity;
    }
}
