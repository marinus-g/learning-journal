package academy.mischok.learningjournal.repository;

import academy.mischok.learningjournal.model.Subject;
import academy.mischok.learningjournal.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository {
    Optional<Subject> findByName(String name);
    List<Subject> findAllByTeacherContaining(String name);
    //List<Subject> findAllByJournalEntry(JournalEntry journalEntry);
    //List<Subject> findByAllJournalEntryAndUser(JournalEntry journalEntry, UserEntity user);
}
