package pl.polsl.fitstat.dtos;

import pl.polsl.fitstat.models.ChallengeEntity;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ChallengeDTO {

    private Long id;

    @NotEmpty(message = "description field must not be empty")
    private String description;

    @NotNull(message = "challenge time cannot be null")
    @Min( value = 1, message = "challenge time must be greater than 0")
    private Integer challengeTime;

    @NotNull(message = "activity id cannot be null")
    @Min( value = 1, message = "activity id must be greater than 0")
    private Long activityId;

    private String activityName;

    public ChallengeDTO() {
    }

    public ChallengeDTO(ChallengeEntity challengeEntity) {
        this.id = challengeEntity.getId();
        this.description = challengeEntity.getDescription();
        this.challengeTime = challengeEntity.getChallengeTime();
        this.activityId = challengeEntity.getActivityEntity().getId();
        this.activityName = challengeEntity.getActivityEntity().getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getChallengeTime() {
        return challengeTime;
    }

    public void setChallengeTime(Integer challengeTime) {
        this.challengeTime = challengeTime;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
