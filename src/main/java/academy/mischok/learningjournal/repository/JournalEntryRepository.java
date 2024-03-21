package academy.mischok.learningjournal.repository;

import academy.mischok.learningjournal.dto.JournalEntryDto;
import academy.mischok.learningjournal.model.JournalEntry;
import academy.mischok.learningjournal.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {

    List<JournalEntry> findJournalEntryByUser_Id(Long id);

    Optional<JournalEntry> findJournalEntryByIdAndUser(Long id, @NonNull UserEntity user);
}
