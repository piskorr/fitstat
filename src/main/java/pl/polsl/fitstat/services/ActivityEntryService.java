package pl.polsl.fitstat.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.polsl.fitstat.dtos.ActivityEntryDTO;
import pl.polsl.fitstat.errors.ResourceNotFoundException;
import pl.polsl.fitstat.models.ActivityEntity;
import pl.polsl.fitstat.models.UserEntity;
import pl.polsl.fitstat.models.ActivityEntryEntity;
import pl.polsl.fitstat.repositories.ActivityEntryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityEntryService {

    private final ActivityEntryRepository repository;
    private final UserService userService;
    private final ActivityService activityService;
    private final UsersChallengeService usersChallengeService;

    public ActivityEntryService(ActivityEntryRepository repository, UserService userService, ActivityService activityService,@Lazy UsersChallengeService usersChallengeService) {
        this.repository = repository;
        this.userService = userService;
        this.activityService = activityService;
        this.usersChallengeService = usersChallengeService;
    }

    public ActivityEntryEntity getActivityEntryById(long entryId) {
        return repository.findById(entryId)
                .filter(activityEntry -> !activityEntry.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("User's activity entry with id: " + entryId + " does not exist!"));
    }

    public ActivityEntryDTO getActivityEntryByIdAndMap(long entryId) {
        return new ActivityEntryDTO(getActivityEntryById(entryId));
    }

    public List<ActivityEntryDTO> getAllCurrentUsersActivityEntries() {
        return repository.findAllByUserEntity_Id(userService.getCurrentUser().getId())
                .stream()
                .filter(activityEntry -> !activityEntry.isDeleted())
                .map(ActivityEntryDTO::new)
                .collect(Collectors.toList());
    }

    public List<ActivityEntryEntity> getAllCurrentUsersActivityEntriesByActivityId(long activityId) {
        return repository.findAllByUserEntity_IdAndActivityEntity_Id(userService.getCurrentUser().getId(), activityId)
                .stream()
                .filter(activityEntry -> !activityEntry.isDeleted())
                .collect(Collectors.toList());
    }

    public List<ActivityEntryEntity> getAllActivityEntriesByActivityIdAndUserId(long activityId, long userId) {
        return repository.findAllByUserEntity_IdAndActivityEntity_Id(userId, activityId)
                .stream()
                .filter(activityEntry -> !activityEntry.isDeleted())
                .collect(Collectors.toList());
    }

    public List<ActivityEntryDTO> getAllCurrentUsersActivityEntriesByActivityIdAndMap(long activityId) {
        return repository.findAllByUserEntity_IdAndActivityEntity_Id(userService.getCurrentUser().getId(), activityId)
                .stream()
                .filter(activityEntry -> !activityEntry.isDeleted())
                .map(ActivityEntryDTO::new)
                .collect(Collectors.toList());
    }

    public List<ActivityEntryDTO> getAllActivityEntriesByActivityId(long activityId) {
        return repository.findAllByActivityEntity_Id(activityId)
                .stream()
                .filter(activityEntry -> !activityEntry.isDeleted())
                .map(ActivityEntryDTO::new)
                .collect(Collectors.toList());
    }

    public ActivityEntryDTO addActivityEntryToCurrentUser(long activityId, ActivityEntryDTO activityEntryDTO) {
        ActivityEntity activity = activityService.getActivityById(activityId);
        activityEntryDTO.setCaloriesBurned(calculateCaloriesBurned(activityEntryDTO.getActivityDuration(), activity.getMET()));
        UserEntity user = userService.getCurrentUser();
        ActivityEntryEntity activityEntry = new ActivityEntryEntity(activityEntryDTO, activity, user);
        repository.save(activityEntry);
        usersChallengeService.updateUsersChallengeTimeByUserIdAndActivityId(user.getId(), activityId, activityEntry.getActivityDuration());
        return new ActivityEntryDTO(activityEntry);
    }

    private int calculateCaloriesBurned(Integer activityDuration, Double MET) {
        //Total calories burned = Duration (in minutes)*(MET*3.5*weight in kg)/200
        return (int) ((activityDuration / 60) * (MET * 3.5 * userService.getCurrentUser().getWeight()) / 200);
    }

    public ActivityEntryDTO updateActivityEntryById(long entryId, ActivityEntryDTO activityEntryDTO) {
        ActivityEntryEntity activityEntry = getActivityEntryById(entryId);
        updateActivityEntry(activityEntry, activityEntryDTO);
        activityEntry.setCaloriesBurned(calculateCaloriesBurned(activityEntry.getActivityDuration(), activityEntry.getActivityEntity().getMET()));
        repository.save(activityEntry);
        return new ActivityEntryDTO(activityEntry);
    }

    private void updateActivityEntry(ActivityEntryEntity usersActivity, ActivityEntryDTO activityEntryDTO) {
        if (activityEntryDTO.getActivityDate() != null)
            usersActivity.setActivityDate(activityEntryDTO.getActivityDate());

        if (activityEntryDTO.getActivityDuration() != null)
            usersActivity.setActivityDuration(activityEntryDTO.getActivityDuration());
    }

    public ActivityEntryDTO deleteActivityEntryById(long usersActivityId) {
        ActivityEntryEntity activityEntry = getActivityEntryById(usersActivityId);
        activityEntry.setDeleted(true);
        repository.save(activityEntry);
        return new ActivityEntryDTO(activityEntry);
    }

    public void deleteAllActivityEntriesByUserId(long userId){
        List<ActivityEntryEntity> activityEntryList = repository.findAllByUserEntity_Id(userId)
                .stream()
                .filter(activityEntry -> !activityEntry.isDeleted())
                .collect(Collectors.toList());

        activityEntryList.forEach(activityEntry -> activityEntry.setDeleted(true));
        repository.saveAll(activityEntryList);
    }
}
