package pl.polsl.fitstat.models;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "users_records")
@Entity
public class UsersRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "record_date")
    private LocalDate recordDate;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private ActivityEntity activityEntity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public UsersRecordEntity() {
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public ActivityEntity getActivityEntity() {
        return activityEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public void setActivityEntity(ActivityEntity activityEntity) {
        this.activityEntity = activityEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
