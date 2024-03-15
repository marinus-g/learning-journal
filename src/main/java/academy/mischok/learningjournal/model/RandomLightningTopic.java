package academy.mischok.learningjournal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RandomLightningTopic {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @ManyToOne
    private Topic topic;
    private Instant timestamp;
    @ManyToOne()
    private UserEntity user;
}
