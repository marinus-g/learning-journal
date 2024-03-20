package academy.mischok.learningjournal.repository;

import academy.mischok.learningjournal.model.RandomLightningTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RandomLightningTopicRepository extends JpaRepository<RandomLightningTopic, Long> {
}
