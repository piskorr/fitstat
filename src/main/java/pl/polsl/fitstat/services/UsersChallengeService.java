package pl.polsl.fitstat.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.polsl.fitstat.dtos.UsersChallengeDTO;
import pl.polsl.fitstat.errors.InsufficientChallengesCountException;
import pl.polsl.fitstat.errors.ResourceNotFoundException;
import pl.polsl.fitstat.models.ActivityEntryEntity;
import pl.polsl.fitstat.models.ChallengeEntity;
import pl.polsl.fitstat.models.UserEntity;
import pl.polsl.fitstat.models.UsersChallengeEntity;
import pl.polsl.fitstat.repositories.UsersChallengeRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@EnableScheduling
public class UsersChallengeService {

    @Value("${my.challenge-count}")
    private int challengesCount;

    private final UsersChallengeRepository repository;
    private final UserService userService;
    private final ActivityEntryService activityEntryService;
    private final ChallengeService challengeService;


    public UsersChallengeService(UsersChallengeRepository repository, UserService userService, ActivityEntryService activityEntryService, ChallengeService challengeService) {
        this.repository = repository;
        this.userService = userService;
        this.activityEntryService = activityEntryService;
        this.challengeService = challengeService;
    }

    public UsersChallengeEntity getUsersChallengeById(long usersChallengeId) {
        return repository.findById(usersChallengeId)
                .filter(usersChallengeEntity -> !usersChallengeEntity.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Users challenge with id: " + usersChallengeId + " does not exist!"));
    }

    public UsersChallengeDTO getUsersChallengeByIdAndMap(long usersChallengeId) {
        UsersChallengeEntity usersChallenge = getUsersChallengeById(usersChallengeId);
        userService.checkRightsToResource(usersChallenge.getUserEntity().getId());
        return new UsersChallengeDTO(usersChallenge);
    }

    public List<UsersChallengeDTO> getCurrentUsersChallenges() {
        return repository.findAllByUserEntity_Id(userService.getCurrentUser().getId())
                .stream()
                .filter(usersChallengeEntity -> !usersChallengeEntity.isDeleted())
                .map(usersChallenge -> new UsersChallengeDTO(usersChallenge, usersChallenge.getChallengeEntity()))
                .collect(Collectors.toList());
    }

    public void addChallengesForAllUsers(int count) {
        long challengeCount = challengeService.getAllChallenges().size();
        if (challengeCount < count)
            throw new InsufficientChallengesCountException("Not enough challenges!");

        List<UserEntity> userEntityList = userService.getAllUsers();
        userEntityList.forEach(userEntity -> {
            int[] challenges = pickRandomNumbers((int) challengeCount, count);
            for (int i : challenges) {
                addChallengeToUser(i, userEntity);
            }
        });
    }

    public void addChallengesForUser(UserEntity user) {
        long challengeCount = challengeService.getAllChallenges().size();
        if (challengeCount < challengesCount)
            throw new InsufficientChallengesCountException("Not enough challenges!");

        int[] challenges = pickRandomNumbers((int) challengeCount, challengesCount);
        for (int i : challenges) {
            addChallengeToUser(i, user);
        }
    }

    //TODO: RAZ NA TYDZIEN
    @Scheduled(fixedDelay = 600000)
    public void resetAllUsersChallenges() {
        System.out.println(LocalDateTime.now() + ": Adding new challenges!");
        deleteAllChallenges();
        addChallengesForAllUsers(challengesCount);
        System.out.println(LocalDateTime.now() + ": Adding done!");
    }

    public UsersChallengeDTO addChallengeToUser(long challengeId, UserEntity userEntity) {
        ChallengeEntity challengeEntity = challengeService.getChallengeById(challengeId);
        UsersChallengeEntity usersChallengeEntity = new UsersChallengeEntity(challengeEntity, userEntity);
        repository.save(usersChallengeEntity);
        return new UsersChallengeDTO(usersChallengeEntity);
    }

    public UsersChallengeDTO updateUsersChallengeTimeById(long challengeId) {
        UsersChallengeEntity usersChallenge = getUsersChallengeById(challengeId);
        if (usersChallenge.getTotalTime() >= usersChallenge.getChallengeEntity().getChallengeTime()) {
            usersChallenge.setCompleted(true);
        }

        repository.save(usersChallenge);
        return new UsersChallengeDTO(usersChallenge);
    }

    private void updateUsersChallengeTime(UsersChallengeEntity usersChallenge) {
        List<ActivityEntryEntity> activities = activityEntryService
                .getAllActivityEntriesByActivityIdAndUserId(
                        usersChallenge.getUserEntity().getId(),
                        usersChallenge.getChallengeEntity().getActivityEntity().getId()
                )
                .stream()
                .filter(activityEntry ->
                        activityEntry.getActivityDate()
                                .isAfter(ChronoLocalDateTime
                                        .from(LocalDateTime
                                                .of(usersChallenge.getDate(), LocalTime.MIDNIGHT))
                                ))
                .collect(Collectors.toList());

        int totalTime = 0;
        for (ActivityEntryEntity activityEntry : activities) {
            totalTime += activityEntry.getActivityDuration();
        }

        usersChallenge.setTotalTime(totalTime);
        repository.save(usersChallenge);
    }

    public void updateUsersChallengeTimeByUserIdAndActivityId(long userId, long activityId, Integer activityDuration) {
        List<UsersChallengeEntity> usersChallenges = repository.findAllByUserEntity_Id(userId)
                .stream()
                .filter(usersChallenge -> !usersChallenge.isDeleted() &&
                        !usersChallenge.isCompleted() &&
                        usersChallenge.getChallengeEntity().getActivityEntity().getId().equals(activityId))
                .collect(Collectors.toList());

        for (UsersChallengeEntity usersChallenge : usersChallenges) {
            usersChallenge.setTotalTime(usersChallenge.getTotalTime() + activityDuration);
            if (usersChallenge.getTotalTime() >= usersChallenge.getChallengeEntity().getChallengeTime())
                usersChallenge.setCompleted(true);
        }

        repository.saveAll(usersChallenges);
    }

    public UsersChallengeDTO deleteChallengeById(long challengeId) {
        UsersChallengeEntity usersChallengeEntity = getUsersChallengeById(challengeId);
        usersChallengeEntity.setDeleted(true);
        repository.save(usersChallengeEntity);
        return new UsersChallengeDTO(usersChallengeEntity);
    }

    public void deleteAllChallenges() {
        List<UsersChallengeEntity> challengeEntityList = repository.findAll()
                .stream()
                .filter(usersChallenge -> !usersChallenge.isDeleted())
                .collect(Collectors.toList());

        challengeEntityList.forEach(usersChallenge -> {
            updateUsersChallengeTime(usersChallenge);
            usersChallenge.setDeleted(true);
        });
        repository.saveAll(challengeEntityList);
    }

    public void deleteAllChallengesByUserId(long userId) {
        List<UsersChallengeEntity> challengeEntityList = repository.findAll()
                .stream()
                .filter(usersChallenge -> !usersChallenge.isDeleted() && usersChallenge.getUserEntity().getId().equals(userId))
                .collect(Collectors.toList());

        challengeEntityList.forEach(usersChallenge -> {
            updateUsersChallengeTime(usersChallenge);
            usersChallenge.setDeleted(true);
        });
        repository.saveAll(challengeEntityList);
    }

    private UsersChallengeDTO deleteChallenge(UsersChallengeEntity usersChallengeEntity) {
        usersChallengeEntity.setDeleted(true);
        repository.save(usersChallengeEntity);
        return new UsersChallengeDTO(usersChallengeEntity);
    }

    private int[] pickRandomNumbers(int max, int size) {
        int[] arr = IntStream.rangeClosed(1, max).toArray();
        Random r = new Random();
        return IntStream.range(0, size)
                .map(i -> {
                    int index = r.nextInt(max - i);
                    int p = arr[index];
                    arr[index] = arr[max - i - 1];
                    return p;
                }).toArray();
    }
}
