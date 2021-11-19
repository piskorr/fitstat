package pl.polsl.fitstat.dtos;

import pl.polsl.fitstat.models.ActivityEntity;

public class ActivityDTO {

    private Long id;
    private String name;
    private String description;
    private Integer caloriesConsumption;
    private Boolean isDeleted;

    public ActivityDTO() {
    }

    public ActivityDTO(ActivityEntity activityEntity) {
        this.id = activityEntity.getId();
        this.name = activityEntity.getName();
        this.description = activityEntity.getDescription();;
        this. caloriesConsumption = activityEntity.getCaloriesConsumption();
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

    public Integer getCaloriesConsumption() {
        return caloriesConsumption;
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

    public void setCaloriesConsumption(Integer caloriesConsumption) {
        this.caloriesConsumption = caloriesConsumption;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
