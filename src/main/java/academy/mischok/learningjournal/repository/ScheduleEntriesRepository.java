package academy.mischok.learningjournal.repository;

import academy.mischok.learningjournal.model.ScheduleEntry;
import academy.mischok.learningjournal.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleEntriesRepository extends JpaRepository<ScheduleEntry, Long> {
}
