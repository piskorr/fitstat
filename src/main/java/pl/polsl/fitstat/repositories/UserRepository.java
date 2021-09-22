package pl.polsl.fitstat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.fitstat.models.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
