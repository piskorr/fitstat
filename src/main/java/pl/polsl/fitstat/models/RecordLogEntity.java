package pl.polsl.fitstat.models;

import pl.polsl.fitstat.dtos.RecordDTO;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "record_logs")
@Entity
public class RecordLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "record_date")
    private LocalDate recordDate;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_historic")
    private Boolean isHistoric;

    @Column(name = "value")
    private Integer value;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private ActivityEntity activityEntity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private UnitEntity unit;

    public RecordLogEntity() {
    }

    public RecordLogEntity(RecordDTO recordDTO, ActivityEntity activity, UserEntity currentUser) {

    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public Boolean isDeleted() {
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

    public Boolean isHistoric() {
        return isHistoric;
    }

    public void setHistoric(Boolean historic) {
        isHistoric = historic;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
