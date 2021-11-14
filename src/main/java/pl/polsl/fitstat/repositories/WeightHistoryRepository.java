package pl.polsl.fitstat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.fitstat.models.WeightHistoryEntity;

@Repository
public interface WeightHistoryRepository extends JpaRepository<WeightHistoryEntity, Long> {
}
