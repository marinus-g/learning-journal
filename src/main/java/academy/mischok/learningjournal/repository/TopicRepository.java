package academy.mischok.learningjournal.repository;



import academy.mischok.learningjournal.model.Subject;
import academy.mischok.learningjournal.model.Topic;
import academy.mischok.learningjournal.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Long> {
  Optional<Topic> findByName(String name);
  List<Topic> findAllByTeachersContaining(String lastName);
 // List<Topic> findAllByJournalEntry(JournalEntry journalEntry);
   // List<Topic> findAllByJournalEntryAndUser(JournalEntry journalEntry, UserEntity user);
    List<Topic> findAllBySubjectContaining(Subject subject);

}
