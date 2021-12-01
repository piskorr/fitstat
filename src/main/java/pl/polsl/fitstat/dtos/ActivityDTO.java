package pl.polsl.fitstat.dtos;

import pl.polsl.fitstat.models.ActivityEntity;

import javax.validation.constraints.*;

public class ActivityDTO {


    private Long id;

    @NotEmpty
    @Size(min = 6, message = "activity name should have at least 4 characters")
    @Size(max = 15, message = "activity name cannot be longer than 25 characters")
    private String name;

    @NotEmpty
    @Size(max = 15, message = "activity description cannot be longer than 255 characters")
    private String description;

    @NotNull
    @Max( value = 20, message = "MET cannot be greater than 20")
    @Min( value = 1, message = "MET cannot be lesser than 1")
    private Double MET;

    private Boolean isDeleted;

    public ActivityDTO() {
    }

    public ActivityDTO(ActivityEntity activityEntity) {
        this.id = activityEntity.getId();
        this.name = activityEntity.getName();
        this.description = activityEntity.getDescription();;
        this.MET = activityEntity.getMET();
        this.isDeleted = activityEntity.isDeleted();

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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMET(Double MET) {
        this.MET = MET;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
