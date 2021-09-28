package pl.polsl.fitstat.models;

import javax.persistence.*;

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

    @Column(name = "calories_consumption")
    private Integer caloriesConsumption;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public ActivityEntity() {
    }

    public ActivityEntity(String name, String description, Integer caloriesConsumption, Boolean isDeleted) {
        this.name = name;
        this.description = description;
        this.caloriesConsumption = caloriesConsumption;
        this.isDeleted = isDeleted;
    }

    public ActivityEntity(Long id, String name, String description, Integer caloriesConsumption, Boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.caloriesConsumption = caloriesConsumption;
        this.isDeleted = isDeleted;
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
}
