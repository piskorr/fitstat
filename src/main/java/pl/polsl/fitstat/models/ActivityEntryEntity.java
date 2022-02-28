package pl.polsl.fitstat.models;

import pl.polsl.fitstat.dtos.ActivityEntryDTO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "activity_entries")
public class ActivityEntryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activity_date")
    private LocalDateTime activityDate;

    @Column(name = "activity_duration")
    private Integer activityDuration;

    @Column(name = "calories_burned")
    private Integer caloriesBurned;


    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private ActivityEntity activityEntity;

    public ActivityEntryEntity() {
    }

    public ActivityEntryEntity(Long id, LocalDateTime activityDate, Integer activityDuration, Integer caloriesBurned) {
        this.id = id;
        this.activityDate = activityDate;
        this.activityDuration = activityDuration;
        this.caloriesBurned = caloriesBurned;
    }

    public ActivityEntryEntity(LocalDateTime activityDate, Integer activityDuration, Integer caloriesBurned, Boolean isDeleted, UserEntity userEntity, ActivityEntity activityEntity) {
        this.activityDate = activityDate;
        this.activityDuration = activityDuration;
        this.caloriesBurned = caloriesBurned;
        this.isDeleted = isDeleted;
        this.userEntity = userEntity;
        this.activityEntity = activityEntity;
    }

    public ActivityEntryEntity(ActivityEntryDTO activityEntryDTO, ActivityEntity activity, UserEntity user) {
        this.activityDate = activityEntryDTO.getActivityDate();
        this.activityDuration = activityEntryDTO.getActivityDuration();
        this.caloriesBurned = activityEntryDTO.getCaloriesBurned();
        this.isDeleted = false;
        this.userEntity = user;
        this.activityEntity = activity;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getActivityDate() {
        return activityDate;
    }

    public Integer getActivityDuration() {
        return activityDuration;
    }

    public Integer getCaloriesBurned() {
        return caloriesBurned;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public ActivityEntity getActivityEntity() {
        return activityEntity;
    }

    public void setCaloriesBurned(Integer caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public void setActivityDate(LocalDateTime activityDate) {
        this.activityDate = activityDate;
    }

    public void setActivityDuration(Integer activityDuration) {
        this.activityDuration = activityDuration;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public void setActivityEntity(ActivityEntity activityEntity) {
        this.activityEntity = activityEntity;
    }
}
