package pl.polsl.fitstat.models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import pl.polsl.fitstat.dtos.ActivityDTO;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "activities")
public class ActivityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "MET")
    private Double MET;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Fetch(FetchMode.JOIN)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "activityEntity")
    Set<ActivityEntryEntity> usersActivities;

    @Fetch(FetchMode.JOIN)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "activityEntity")
    Set<PlannedActivityEntity> plannedActivities;

    @Fetch(FetchMode.JOIN)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "activityEntity")
    Set<RecordLogEntity> usersRecords;

    @Fetch(FetchMode.JOIN)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "activityEntity")
    Set<ChallengeEntity> challengeActivities;

    public ActivityEntity() {
    }

    public ActivityEntity(String name, String description, Double MET, Boolean isDeleted) {
        this.name = name;
        this.description = description;
        this.MET = MET;
        this.isDeleted = isDeleted;
    }

    public ActivityEntity(Long id, String name, String description, Double MET, Boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.MET = MET;
        this.isDeleted = isDeleted;
    }

    public ActivityEntity(ActivityDTO activityDTO) {
        this.name = activityDTO.getName();
        this.description = activityDTO.getDescription();
        this.MET = activityDTO.getMET();
        this.isDeleted = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMET(Double caloriesConsumption) {
        this.MET = caloriesConsumption;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getMET() {
        return MET;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }
}
