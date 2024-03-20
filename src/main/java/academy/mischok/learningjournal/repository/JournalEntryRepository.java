package academy.mischok.learningjournal.repository;

import academy.mischok.learningjournal.model.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
}
