package academy.mischok.learningjournal.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
public class JournalEntryDto {

    public JournalEntryDto(Long id, Instant timestamp, Long subject, Long topic, String subjectName, String topicName, String title, String content, List<String> tag) {
        this.id = id;
        this.timestamp = timestamp;
        this.subject = subject;
        this.topic = topic;
        this.title = title;
        this.topicName = topicName;
        this.subjectName = subjectName;
        this.content = content;
        this.tag = tag;
        this.shortEntry = content.substring(0, Math.min(content.length(), 280));
    }

    private Long id;
    private Instant timestamp;
    private Long user;

    private Long subject;
    private String subjectName; // THYMELEAF XD MOMENT
    private Long topic;
    private String topicName; // THYMELEAF XD MOMENT

    private String title;

    private String shortEntry;
    private String content;

    private List<String> tag;

}