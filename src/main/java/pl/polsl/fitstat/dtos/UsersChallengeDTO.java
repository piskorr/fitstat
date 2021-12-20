package pl.polsl.fitstat.dtos;

import pl.polsl.fitstat.models.ChallengeEntity;
import pl.polsl.fitstat.models.UsersChallengeEntity;

import java.time.LocalDate;

public class UsersChallengeDTO {

    private Long id;
    private LocalDate date;
    private Integer totalTime;
    private Long challengeId;
    private Long userId;
    private Boolean isCompleted;
    private Boolean isDeleted;
    private ChallengeDTO challengeDTO;

    public UsersChallengeDTO() {
    }

    public UsersChallengeDTO(UsersChallengeEntity usersChallenge) {
        this.id = usersChallenge.getId();
        this.date = usersChallenge.getDate();
        this.totalTime = usersChallenge.getTotalTime();
        this.isCompleted = usersChallenge.isCompleted();
        this.isDeleted = usersChallenge.isDeleted();
        this.challengeId = usersChallenge.getChallengeEntity().getId();
        this.userId = usersChallenge.getUserEntity().getId();
    }

    public UsersChallengeDTO(UsersChallengeEntity usersChallenge, ChallengeEntity challengeEntity) {
        this.id = usersChallenge.getId();
        this.date = usersChallenge.getDate();
        this.totalTime = usersChallenge.getTotalTime();
        this.isCompleted = usersChallenge.isCompleted();
        this.isDeleted = usersChallenge.isDeleted();
        this.challengeDTO = new ChallengeDTO(challengeEntity);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public Long getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Long challengeId) {
        this.challengeId = challengeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public ChallengeDTO getChallengeDTO() {
        return challengeDTO;
    }

    public void setChallengeDTO(ChallengeDTO challengeDTO) {
        this.challengeDTO = challengeDTO;
    }
}
