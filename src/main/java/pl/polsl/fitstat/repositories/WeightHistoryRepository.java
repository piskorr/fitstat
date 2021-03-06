package pl.polsl.fitstat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.fitstat.models.WeightHistoryEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeightHistoryRepository extends JpaRepository<WeightHistoryEntity, Long> {

    List<WeightHistoryEntity> findAllByUserEntity_IdOrderByDateDesc(Long userEntity_id);

    Optional<WeightHistoryEntity> findByUserEntity_IdAndIsHistoricFalse(Long userEntity_id);

    Optional<WeightHistoryEntity> findFirstByUserEntity_IdOrderByDateDesc(Long userEntity_id);
}
