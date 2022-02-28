package pl.polsl.fitstat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.polsl.fitstat.models.RecordLogEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordLogRepository extends JpaRepository<RecordLogEntity, Long> {

    List<RecordLogEntity> findAllByActivityEntity_IdAndUserEntity_Id(Long activityEntity_id, Long userEntity_id);

    Optional<RecordLogEntity> findByActivityEntity_IdAndUserEntity_IdAndIsHistoricFalseAndIsDeletedFalse(Long activityEntity_id, Long userEntity_id);

    List<RecordLogEntity> findAllByUserEntity_IdAndIsHistoricFalse(Long userEntity_id);

    List<RecordLogEntity> findAllByUserEntity_Id(Long userEntity_id);

    Optional<RecordLogEntity> findAllByUserEntity_IdAndUnit_IdAndActivityEntity_IdAndIsHistoricFalse(Long userEntity_id, Long unit_id, Long activityEntity_id);

}
