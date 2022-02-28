package pl.polsl.fitstat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.polsl.fitstat.models.ActivityEntity;

import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, Long> {

    @Query("select distinct a from ActivityEntity a where a.name = :name and a.isDeleted = false")
    Optional<ActivityEntity> findByName(String name);

}
