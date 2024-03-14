package academy.mischok.learningjournal.repository;



import academy.mischok.learningjournal.model.Subject;
import academy.mischok.learningjournal.model.Topic;
import academy.mischok.learningjournal.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface TopicRepository {
  Optional<Topic> findByName(String name);
  List<Topic> findAllByTeachersContaining(String lastName);
 // List<Topic> findAllByJournalEntry(JournalEntry journalEntry);
   // List<Topic> findAllByJournalEntryAndUser(JournalEntry journalEntry, UserEntity user);
    List<Topic> findAllBySubjectContaining(Subject subject);

}
