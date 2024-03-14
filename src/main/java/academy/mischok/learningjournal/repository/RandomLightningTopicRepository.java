package academy.mischok.learningjournal.repository;

import academy.mischok.learningjournal.model.UserEntity;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RandomLightningTopicRepository extends JpaRepository<UserEntity, Long> {
}
