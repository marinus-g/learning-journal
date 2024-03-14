package academy.mischok.learningjournal.repository;

import academy.mischok.learningjournal.model.SchoolClass;
import academy.mischok.learningjournal.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface SchoolClassRepository {
    Optional<SchoolClass> findByName(String name);
    Optional<SchoolClass> findByStudentsContaining(UserEntity student);
    List<SchoolClass> findAllByDescriptionLike(String description);
    List<SchoolClass> findAllByNameLike(String name);
}
