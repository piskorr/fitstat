package pl.polsl.fitstat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.fitstat.models.PlannedActivityEntity;

@Repository
public interface PlannedActivityRepository extends JpaRepository<PlannedActivityEntity, Long> {
}
