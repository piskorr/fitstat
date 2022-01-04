package pl.polsl.fitstat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.fitstat.models.UnitEntity;

@Repository
public interface UnitRepository extends JpaRepository<UnitEntity, Long> {

}
