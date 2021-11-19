package pl.polsl.fitstat.services;

import org.springframework.stereotype.Service;
import pl.polsl.fitstat.dtos.ActivityDTO;
import pl.polsl.fitstat.errors.ResourceAlreadyExistException;
import pl.polsl.fitstat.errors.ResourceNotFoundException;
import pl.polsl.fitstat.models.ActivityEntity;
import pl.polsl.fitstat.repositories.ActivityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    private final ActivityRepository repository;

    public ActivityService(ActivityRepository repository) {
        this.repository = repository;
    }

    public ActivityEntity getActivityById(long activityId) {
        return repository.findById(activityId)
                .filter(activityEntity -> !activityEntity.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Activity with id: " + activityId + " does not exist!"));
    }

    public ActivityEntity getActivityByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Activity with name: " + name + " does not exist!"));
    }

    public ActivityDTO getActivityByNameAndMap(String name) {
        return new ActivityDTO(getActivityByName(name));
    }

    public ActivityDTO getActivityByIdAndMap(long activityId) {
        return new ActivityDTO(getActivityById(activityId));
    }

    public List<ActivityDTO> getAllActivities() {
        return repository.findAll()
                .stream()
                .filter(activityEntity -> !activityEntity.isDeleted())
                .map(ActivityDTO::new)
                .collect(Collectors.toList());
    }

    public ActivityDTO addNewActivity(ActivityDTO activityDTO) {
        repository.findByName(activityDTO.getName())
                .orElseThrow(() -> new ResourceAlreadyExistException("Activity with name: " + activityDTO.getName() + " already exists!"));

        ActivityEntity newActivity = new ActivityEntity(activityDTO);
        repository.save(newActivity);
        return new ActivityDTO(newActivity);
    }

    public ActivityDTO updateActivityById(ActivityDTO activityDTO) {
        ActivityEntity activity = getActivityById(activityDTO.getId());

        updateActivity()




    }

}
