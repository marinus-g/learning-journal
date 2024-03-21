package academy.mischok.learningjournal.service;

import academy.mischok.learningjournal.dto.JournalEntryDto;
import academy.mischok.learningjournal.model.JournalEntry;
import academy.mischok.learningjournal.model.Tag;
import academy.mischok.learningjournal.model.UserEntity;
import academy.mischok.learningjournal.repository.JournalEntryRepository;
import academy.mischok.learningjournal.repository.SubjectRepository;
import academy.mischok.learningjournal.repository.TopicRepository;
import academy.mischok.learningjournal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class JournalService {

    private final JournalEntryRepository journalEntryRepository;
    private final SubjectRepository subjectRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public JournalService(JournalEntryRepository journalEntryRepository,
                          SubjectRepository subjectRepository, TopicRepository topicRepository, UserRepository userRepository) {
        this.journalEntryRepository = journalEntryRepository;
        this.subjectRepository = subjectRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }


    public boolean createJournalEntry(JournalEntryDto journalEntryDto) {
        final UserEntity user = userRepository.findById(journalEntryDto.getUser()).orElseThrow();
        JournalEntry journalEntry = JournalEntry.builder()
                .title(journalEntryDto.getTitle())
                .content(journalEntryDto.getContent())
                .subject(subjectRepository.findById(journalEntryDto.getSubject()).orElseThrow())
                .topic(topicRepository.findById(journalEntryDto.getTopic()).orElseThrow())
                .timestamp(journalEntryDto.getTimestamp())
                .user(user)
                .build();
        journalEntry = journalEntryRepository.save(journalEntry);
        return journalEntry.getId() != null;
    }


    public List<JournalEntryDto> getJournalEntriesByUser(Long userId) {
        return this.journalEntryRepository.findJournalEntryByUser_Id(userId)
                .stream()
                .map(this::toDto).toList();
    }

    public JournalEntryDto toDto(JournalEntry entry) {
        return new JournalEntryDto(
                entry.getId(),
                entry.getTimestamp(),
                entry.getSubject().getId(),
                entry.getTopic().getId(),
                entry.getSubject().getName(),
                entry.getTopic().getName(),
                entry.getTitle(),
                entry.getContent(),
                entry.getTag().stream().map(Tag::getName).collect(Collectors.toList())
        );
    }

    public JournalEntryDto findJournalByIdAndUser(Long id, UserEntity userEntity) {
        return this.journalEntryRepository.findJournalEntryByIdAndUser(id, userEntity)
                .map(this::toDto)
                .orElseThrow();
    }
}
