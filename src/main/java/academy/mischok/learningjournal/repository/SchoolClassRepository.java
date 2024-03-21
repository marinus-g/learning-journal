package academy.mischok.learningjournal.repository;

import academy.mischok.learningjournal.model.SchoolClass;
import academy.mischok.learningjournal.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass,Long> {
    Optional<SchoolClass> findByName(String name);
    Optional<SchoolClass> findByStudentsContaining(UserEntity student);
    List<SchoolClass> findAllByLongDescriptionLike(String description);
    List<SchoolClass> findAllByShortDescriptionLike(String description);
    List<SchoolClass> findAllByNameLike(String name);
}
