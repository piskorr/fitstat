package pl.polsl.fitstat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.fitstat.models.UsersRecordEntity;

@Repository
public interface UsersRecordRepository extends JpaRepository<UsersRecordEntity, Long> {
}
