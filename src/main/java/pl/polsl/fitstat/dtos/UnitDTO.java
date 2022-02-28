package pl.polsl.fitstat.dtos;

import pl.polsl.fitstat.models.UnitEntity;

public class UnitDTO {

    private Long id;

    private String unit;

    public UnitDTO(UnitEntity unitById) {
        this.id = unitById.getId();
        this.unit = unitById.getUnit();
    }

    public UnitDTO(Long id, String unit) {
        this.id = id;
        this.unit = unit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
