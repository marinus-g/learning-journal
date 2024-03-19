package academy.mischok.learningjournal.repository;

import academy.mischok.learningjournal.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByFirstNameIgnoreCase(String firstName);
    Optional<UserEntity> findByLastNameIgnoreCase(String lastName);

    Optional<UserEntity> findByFirstNameOrLastNameAllIgnoreCase(String first, String last);
    Optional<UserEntity> findByUsernameIgnoreCase(String userName);
    Optional<UserEntity> findByEmailIgnoreCase(String email);

    List<UserEntity> findAllBySchoolClass(SchoolClass schoolClass);
    List<UserEntity> findAllByRolesContaining(Role role);
    List<UserEntity> findAllByTeachingTopicsContaining(Topic topic);
    List<UserEntity> findAllByTeachingSubjectsContaining(Subject subject);
}
