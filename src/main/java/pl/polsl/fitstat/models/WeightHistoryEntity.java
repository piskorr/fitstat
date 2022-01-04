package pl.polsl.fitstat.models;


import pl.polsl.fitstat.dtos.WeightEntryDTO;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "weight_history")
public class WeightHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "is_historic")
    private Boolean isHistoric;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public WeightHistoryEntity() {
    }

    public WeightHistoryEntity(UserEntity userEntity) {
        this.weight = userEntity.getWeight().doubleValue();
        this.userEntity = userEntity;
        this.date = LocalDate.now();
        this.isDeleted = false;
        this.isHistoric = false;
    }

    public WeightHistoryEntity(Float weight, UserEntity userEntity) {
        this.weight = Double.valueOf(weight);
        this.userEntity = userEntity;
        this.date = LocalDate.now();
        this.isDeleted = false;
        this.isHistoric = false;
    }

    public WeightHistoryEntity(WeightEntryDTO weightEntryDTO, UserEntity user) {
        this.weight = weightEntryDTO.getWeight();
        this.userEntity = user;
        this.date = weightEntryDTO.getDate();
        this.isDeleted = false;
        this.isHistoric = false;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }


    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Boolean getHistoric() {
        return isHistoric;
    }

    public void setHistoric(Boolean historic) {
        isHistoric = historic;
    }
}
