package academy.mischok.learningjournal.repository;

import academy.mischok.learningjournal.model.Role;
import academy.mischok.learningjournal.model.SchoolClass;
import academy.mischok.learningjournal.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByFirstNameIgnoreCase(String firstName);
    Optional<UserEntity> findByLastNameIgnoreCase(String lastName);

    Optional<UserEntity> findByFirstOrLastNameAllIgnoreCase(String first, String last);
    List<UserEntity> findAllBySchoolClass(SchoolClass schoolClass);
    List<UserEntity> findAllByRolesContaining(Role role);
}
