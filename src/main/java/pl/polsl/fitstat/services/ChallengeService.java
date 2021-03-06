package pl.polsl.fitstat.services;

import org.springframework.stereotype.Service;
import pl.polsl.fitstat.dtos.ChallengeDTO;
import pl.polsl.fitstat.errors.ResourceNotFoundException;
import pl.polsl.fitstat.models.ActivityEntity;
import pl.polsl.fitstat.models.ChallengeEntity;
import pl.polsl.fitstat.models.UserEntity;
import pl.polsl.fitstat.repositories.ChallengeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChallengeService {

    private final ChallengeRepository repository;
    private final ActivityService activityService;

    public ChallengeService(ChallengeRepository repository, ActivityService activityService) {
        this.repository = repository;
        this.activityService = activityService;
    }

    public ChallengeEntity getChallengeById(long challengeId) {
        return repository.findById(challengeId)
                .filter(challengeEntity -> !challengeEntity.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Challenge with id: " + challengeId + " does not exist!"));
    }

    public ChallengeDTO getChallengeByIdAndMap(long challengeId) {
        return new ChallengeDTO(getChallengeById(challengeId));
    }

    public List<ChallengeEntity> getAllChallenges() {
        return new ArrayList<>(repository.findAll());
    }

    public List<ChallengeDTO> getAllChallengesAndMap() {
        return repository.findAll()
                .stream()
                .filter(challengeEntity -> !challengeEntity.isDeleted())
                .map(ChallengeDTO::new)
                .collect(Collectors.toList());
    }

    public ChallengeDTO addNewChallenge(ChallengeDTO challengeDTO) {
        ActivityEntity activity = activityService.getActivityById(challengeDTO.getActivityId());
        ChallengeEntity newChallenge = new ChallengeEntity(challengeDTO, activity);
        repository.save(newChallenge);
        return new ChallengeDTO(newChallenge);
    }

    public ChallengeDTO updateChallengeById(long challengeId, ChallengeDTO challengeDTO) {
        ChallengeEntity challengeEntity = getChallengeById(challengeId);
        updateChallenge(challengeEntity, challengeDTO);
        repository.save(challengeEntity);
        return new ChallengeDTO(challengeEntity);
    }

    private void updateChallenge(ChallengeEntity challengeEntity, ChallengeDTO challengeDTO) {
        if (challengeDTO.getChallengeTime() != null) {
            challengeEntity.setChallengeTime(challengeDTO.getChallengeTime());
        }

        if (challengeDTO.getDescription() != null) {
            challengeEntity.setDescription(challengeDTO.getDescription());
        }

        if (challengeDTO.getActivityId() != null) {
            ActivityEntity activity = activityService.getActivityById(challengeDTO.getActivityId());
            challengeEntity.setActivityEntity(activity);
        }
    }

    public void deleteChallengeById(long challengeId) {
        ChallengeEntity challengeEntity = getChallengeById(challengeId);
        challengeEntity.setDeleted(true);
        repository.save(challengeEntity);
    }

}
