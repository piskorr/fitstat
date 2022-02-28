package pl.polsl.fitstat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.polsl.fitstat.models.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("select distinct u from UserEntity u where u.username = :username and u.isDeleted = false")
    Optional<UserEntity> findByUsername(String username);

    @Query("select distinct u from UserEntity u where u.email = :email and u.isDeleted = false")
    Optional<UserEntity> findByEmail(String email);

}
