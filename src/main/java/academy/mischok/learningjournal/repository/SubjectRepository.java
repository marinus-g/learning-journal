package academy.mischok.learningjournal.repository;

import academy.mischok.learningjournal.model.JournalEntry;
import academy.mischok.learningjournal.model.Subject;
import academy.mischok.learningjournal.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByName(String name);
    /*
    List<Subject> findAllByTeacherContaining(String name);
    List<Subject> findAllByJournalEntry(JournalEntry journalEntry);
    List<Subject> findByAllJournalEntryAndUser(JournalEntry journalEntry, UserEntity user);

     */
}
