package pl.polsl.fitstat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.fitstat.models.UsersActivityEntity;

@Repository
public interface UsersActivityRepository extends JpaRepository<UsersActivityEntity, Long> {
}
