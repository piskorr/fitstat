package pl.polsl.fitstat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.fitstat.models.CustomActivityEntity;

@Repository
public interface CustomActivityRepository extends JpaRepository<CustomActivityEntity, Long> {
}