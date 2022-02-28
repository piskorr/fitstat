package pl.polsl.fitstat.services;

import org.springframework.stereotype.Service;
import pl.polsl.fitstat.dtos.WeightEntryDTO;
import pl.polsl.fitstat.errors.ResourceNotFoundException;
import pl.polsl.fitstat.models.UserEntity;
import pl.polsl.fitstat.models.WeightHistoryEntity;
import pl.polsl.fitstat.repositories.WeightHistoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeightHistoryService {

    private final WeightHistoryRepository repository;
    private final UserService userService;

    public WeightHistoryService(WeightHistoryRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public WeightHistoryEntity getWeightEntryById(long entryId) {
        return repository.findById(entryId)
                .filter(weightHistoryEntity -> !weightHistoryEntity.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("User's weight entry with id: " + entryId + " does not exist!"));
    }

    public WeightEntryDTO getWeightEntryByIdAndMap(long entryId) {
        WeightHistoryEntity weightHistoryEntity = getWeightEntryById(entryId);
        userService.checkRightsToResource(weightHistoryEntity.getUserEntity().getId());
        return new WeightEntryDTO(weightHistoryEntity);
    }

    public List<WeightEntryDTO> getCurrentUsersWeightEntries() {
        return repository.findAllByUserEntity_IdOrderByDateDesc(userService.getCurrentUser().getId())
                .stream()
                .filter(weightHistoryEntity -> !weightHistoryEntity.isDeleted())
                .map(WeightEntryDTO::new)
                .collect(Collectors.toList());
    }

    public WeightEntryDTO addNewWeightEntryToCurrentUser(WeightEntryDTO weightEntryDTO) {
        UserEntity user = userService.getCurrentUser();
        WeightHistoryEntity newWeight = new WeightHistoryEntity(weightEntryDTO, user);
        repository.findFirstByUserEntity_IdOrderByDateDesc(user.getId())
                .ifPresent(oldWeight -> {
                    if(oldWeight.getDate().isAfter(newWeight.getDate()) ) {
                        newWeight.setHistoric(true);
                    }else{
                        oldWeight.setHistoric(true);
                        userService.changeCurrentUsersWeight(newWeight.getWeight());
                    }
                });
        repository.save(newWeight);
        return new WeightEntryDTO(newWeight);
    }

    public void addNewWeightEntry(WeightHistoryEntity weightHistoryEntity) {
        repository.save(weightHistoryEntity);
    }

    public void addFirstWeightEntry(WeightHistoryEntity weightHistoryEntity) {
        repository.save(weightHistoryEntity);
    }

    public WeightEntryDTO deleteWeightEntryById(long entryId) {
        WeightHistoryEntity weightHistoryEntity = getWeightEntryById(entryId);
        userService.checkRightsToResource(weightHistoryEntity.getUserEntity().getId());
        weightHistoryEntity.setDeleted(true);
        repository.save(weightHistoryEntity);
        return new WeightEntryDTO(weightHistoryEntity);
    }

}
