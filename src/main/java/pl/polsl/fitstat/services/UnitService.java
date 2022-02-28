package pl.polsl.fitstat.services;

import org.springframework.stereotype.Service;
import pl.polsl.fitstat.dtos.UnitDTO;
import pl.polsl.fitstat.errors.ResourceNotFoundException;
import pl.polsl.fitstat.models.UnitEntity;
import pl.polsl.fitstat.repositories.UnitRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnitService {

    private final UnitRepository repository;

    public UnitService(UnitRepository repository) {
        this.repository = repository;
    }


    public UnitEntity getUnitById(long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unit with id: + id + does not exist!"));
    }

    public UnitDTO getUnitByIdAndMap(long id){
        return new UnitDTO(getUnitById(id));
    }

    public List<UnitDTO> getAllUnits(){
        return repository.findAll()
                .stream()
                .map(UnitDTO::new)
                .collect(Collectors.toList());
    }

}
