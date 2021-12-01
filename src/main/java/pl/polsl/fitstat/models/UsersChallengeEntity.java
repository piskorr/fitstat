package pl.polsl.fitstat.models;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users_challenges")
public class UsersChallengeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "total_time")
    private Integer totalTime;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private ChallengeEntity challengeEntity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public UsersChallengeEntity() {
    }

    public UsersChallengeEntity(ChallengeEntity challengeEntity, UserEntity userEntity) {
        this.date = LocalDate.now();
        this.totalTime = 0;
        this.isCompleted = false;
        this.isDeleted = false;
        this.challengeEntity = challengeEntity;
        this.userEntity = userEntity;
    }

    public Long getId() {
        return id;
    }

    public Boolean isCompleted() {
        return isCompleted;
    }

    public LocalDate getDate() {
        return date;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public ChallengeEntity getChallengeEntity() {
        return challengeEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public void setChallengeEntity(ChallengeEntity challengeEntity) {
        this.challengeEntity = challengeEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }
}
