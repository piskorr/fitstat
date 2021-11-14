package pl.polsl.fitstat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.fitstat.models.UsersChallengeEntity;

@Repository
public interface UsersChallengeRepository extends JpaRepository<UsersChallengeEntity, Long> {
}
