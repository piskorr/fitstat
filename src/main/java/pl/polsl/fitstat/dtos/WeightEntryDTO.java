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

    private Long userId;

    public WeightEntryDTO() {
    }

    public WeightEntryDTO(Double weight, LocalDate date, Long userId) {
        this.weight = weight;
        this.date = date;
        this.userId = userId;
    }

    public WeightEntryDTO(WeightHistoryEntity weightEntryById) {

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
}
