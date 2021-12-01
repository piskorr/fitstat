package pl.polsl.fitstat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.fitstat.models.ActivityEntryEntity;

import java.util.List;

@Repository
public interface ActivityEntryRepository extends JpaRepository<ActivityEntryEntity, Long> {

    //Optional<UsersActivityEntity> findByActivityEntity_Name(String activityEntity_name);

    List<ActivityEntryEntity> findAllByUserEntity_Id(Long userEntity_id);

    List<ActivityEntryEntity> findAllByUserEntity_IdAndActivityEntity_Id(Long userEntity_id, Long activityEntity_id);

    List<ActivityEntryEntity> findAllByActivityEntity_Id(Long activityEntity_id);
}
