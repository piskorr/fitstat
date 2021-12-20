package pl.polsl.fitstat.dtos;

public class CaloriesSummaryDTO {

    private String day;
    private Double calories;

    public CaloriesSummaryDTO() {
    }

    public CaloriesSummaryDTO(String day, Double calories) {
        this.day = day;
        this.calories = calories;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }
}
