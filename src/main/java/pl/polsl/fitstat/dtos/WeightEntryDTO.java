package pl.polsl.fitstat.dtos;

import pl.polsl.fitstat.models.WeightHistoryEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class WeightEntryDTO {

    private Long id;

    @NotNull(message = "weight cannot be null")
    @Min(value = 1, message = "Weight value must be greater than 0")
    private Double weight;

    private LocalDate date;

    private Boolean isHistoric;

    private Boolean isDeleted;

    private Long userId;

    public WeightEntryDTO() {
    }

    public WeightEntryDTO(WeightHistoryEntity weightHistoryEntity) {
        this.id = weightHistoryEntity.getId();
        this.weight = weightHistoryEntity.getWeight();
        this.date = weightHistoryEntity.getDate();
        this.isHistoric = weightHistoryEntity.getHistoric();
        this.isDeleted = weightHistoryEntity.isDeleted();
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
