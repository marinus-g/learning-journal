package academy.mischok.learningjournal.service;

import academy.mischok.learningjournal.repository.JournalEntryRepository;
import org.springframework.stereotype.Service;

@Service
public class JournalService {

    private final JournalEntryRepository journalEntryRepository;

    public JournalService(JournalEntryRepository journalEntryRepository) {
        this.journalEntryRepository = journalEntryRepository;
    }


}
