package pl.polsl.fitstat.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users_activities")
public class UsersActivityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activity_date")
    private LocalDateTime activityDate;

    @Column(name = "activity_duration")
    private Integer activityDuration;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private ActivityEntity activityEntity;

    public UsersActivityEntity() {
    }

    public UsersActivityEntity(Long id, LocalDateTime activityDate, Integer activityDuration) {
        this.id = id;
        this.activityDate = activityDate;
        this.activityDuration = activityDuration;
    }

    public void setActivityDate(LocalDateTime activityDate) {
        this.activityDate = activityDate;
    }

    public void setActivityDuration(Integer activityDuration) {
        this.activityDuration = activityDuration;
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
}
