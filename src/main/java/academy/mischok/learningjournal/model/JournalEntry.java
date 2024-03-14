package academy.mischok.learningjournal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @Column(name = "entry")
    private String entry;

    @ManyToMany()
    private List<Tag> tag;
}
