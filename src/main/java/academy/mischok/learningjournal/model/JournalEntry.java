package academy.mischok.learningjournal.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JournalEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @ManyToOne()
    private UserEntity user;

    @Column(name = "timestamp")
    private Instant timestamp;

    @ManyToOne()
    private Subject subject;

    @ManyToOne
    private Topic topic;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @ManyToMany()
    private List<Tag> tag;
}
