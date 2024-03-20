package academy.mischok.learningjournal.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
public class JournalEntryDto {

    public JournalEntryDto(Long id, Instant timestamp, Long subject, Long topic, String subjectName, String topicName, String title, String entry, List<String> tag) {
        this.id = id;
        this.timestamp = timestamp;
        this.subject = subject;
        this.topic = topic;
        this.title = title;
        this.topicName = topicName;
        this.subjectName = subjectName;
        this.entry = entry;
        this.tag = tag;
        this.shortEntry = entry.substring(0, Math.min(entry.length(), 280));
    }

    private Long id;
    private Instant timestamp;

    private Long subject;
    private String subjectName; // THYMELEAF XD MOMENT
    private Long topic;
    private String topicName; // THYMELEAF XD MOMENT

    private String title;

    private String shortEntry;
    private String entry;

    private List<String> tag;

}