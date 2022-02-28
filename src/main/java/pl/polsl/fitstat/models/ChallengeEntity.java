package pl.polsl.fitstat.models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import pl.polsl.fitstat.dtos.ChallengeDTO;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "challenges")
public class ChallengeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "challenge_time")
    private Integer challengeTime;

    @Column(name="is_deleted")
    private Boolean isDeleted;

    @Fetch(FetchMode.JOIN)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "challengeEntity")
    private Set<UsersChallengeEntity> usersChallenges;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private ActivityEntity activityEntity;

    public ChallengeEntity(ChallengeDTO challengeDTO, ActivityEntity activity) {
        this.description= challengeDTO.getDescription();
        this.challengeTime = challengeDTO.getChallengeTime();
        this.activityEntity = activity;
        this.isDeleted = false;
    }

    public ChallengeEntity() {

    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Integer getChallengeTime() {
        return challengeTime;
    }

    public void setChallengeTime(Integer challengeTime) {
        this.challengeTime = challengeTime;
    }

    public ActivityEntity getActivityEntity() {
        return activityEntity;
    }

    public void setActivityEntity(ActivityEntity activityEntity) {
        this.activityEntity = activityEntity;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
