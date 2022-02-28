package pl.polsl.fitstat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.fitstat.models.UsersChallengeEntity;

import java.util.List;

@Repository
public interface UsersChallengeRepository extends JpaRepository<UsersChallengeEntity, Long> {

    List<UsersChallengeEntity> findAllByUserEntity_Id(Long userEntity_id);

}
