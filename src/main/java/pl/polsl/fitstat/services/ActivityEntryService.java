package pl.polsl.fitstat.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.polsl.fitstat.dtos.ActivityEntryDTO;
import pl.polsl.fitstat.dtos.CaloriesSummaryDTO;
import pl.polsl.fitstat.errors.NoPermissionToResourceException;
import pl.polsl.fitstat.errors.ResourceNotFoundException;
import pl.polsl.fitstat.models.ActivityEntity;
import pl.polsl.fitstat.models.UserEntity;
import pl.polsl.fitstat.models.ActivityEntryEntity;
import pl.polsl.fitstat.repositories.ActivityEntryRepository;

import org.joda.time.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ActivityEntryService {

    private final ActivityEntryRepository repository;
    private final UserService userService;
    private final ActivityService activityService;
    private final UsersChallengeService usersChallengeService;

    public ActivityEntryService(ActivityEntryRepository repository, UserService userService, ActivityService activityService, @Lazy UsersChallengeService usersChallengeService) {
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
        ActivityEntryEntity activityEntry = getActivityEntryById(entryId);
        userService.checkRightsToResource(activityEntry.getUserEntity().getId());
        return new ActivityEntryDTO(activityEntry);
    }

    public List<ActivityEntryDTO> getAllCurrentUsersActivityEntries() {
        return repository.findAllByUserEntity_Id(userService.getCurrentUser().getId())
                .stream()
                .filter(activityEntry -> !activityEntry.isDeleted())
                .map(ActivityEntryDTO::new)
                .collect(Collectors.toList());
    }

    public List<ActivityEntryDTO> getCurrentUsersTodaysActivityEntries() {
        return repository.findAllByUserEntity_Id(userService.getCurrentUser().getId())
                .stream()
                .filter(activityEntry -> !activityEntry.isDeleted() && checkIsToday(activityEntry.getActivityDate()))
                .map(ActivityEntryDTO::new)
                .collect(Collectors.toList());
    }

    public List<CaloriesSummaryDTO> getCurrentUsersWeeklyCalories() {
        Map<Integer, Double> result = new HashMap<>();
        for (int i = 1; i < 8; i++) {
            result.put(i, 0.0);
        }

        List<ActivityEntryEntity> weekList = repository.findAllByUserEntity_Id(userService.getCurrentUser().getId())
                .stream()
                .filter(activityEntry -> !activityEntry.isDeleted() && checkIsThisWeek(activityEntry.getActivityDate()))
                .collect(Collectors.toList());

        weekList.forEach(activityEntry -> {
            result.forEach((integer, aDouble) ->
            {
                if (activityEntry.getActivityDate().getDayOfWeek().getValue() == integer) {
                    Double calories = result.get(integer);
                    calories += activityEntry.getCaloriesBurned();
                    result.put(integer, calories);
                }
            });
        });

        List<CaloriesSummaryDTO> summary = new ArrayList<>();
        String[] strDays = new String[]{"NULL","Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        result.forEach((integer, aDouble) ->
        {
            summary.add(new CaloriesSummaryDTO(strDays[integer], aDouble));
        });

        return summary;
    }

    private boolean checkIsToday(LocalDateTime localDateTime) {
        LocalDate localDate = localDateTime.toLocalDate();
        return localDate.isEqual(LocalDate.now());
    }

    private boolean checkIsThisWeek(LocalDateTime localDateTime) {
        org.joda.time.LocalDate now = org.joda.time.LocalDate.now();
        org.joda.time.LocalDate localDate = new org.joda.time.LocalDate(localDateTime.getYear(), localDateTime.getMonth().getValue(), localDateTime.getDayOfMonth());
        return now.weekOfWeekyear().get() == localDate.weekOfWeekyear().get();
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
        userService.checkRightsToResource(activityEntry.getUserEntity().getId());
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
        userService.checkRightsToResource(activityEntry.getUserEntity().getId());
        activityEntry.setDeleted(true);
        repository.save(activityEntry);
        return new ActivityEntryDTO(activityEntry);
    }

    public void deleteAllActivityEntriesByUserId(long userId) {
        List<ActivityEntryEntity> activityEntryList = repository.findAllByUserEntity_Id(userId)
                .stream()
                .filter(activityEntry -> !activityEntry.isDeleted())
                .collect(Collectors.toList());

        activityEntryList.forEach(activityEntry -> activityEntry.setDeleted(true));
        repository.saveAll(activityEntryList);
    }


}
