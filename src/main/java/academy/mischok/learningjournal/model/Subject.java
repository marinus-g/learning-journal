package academy.mischok.learningjournal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subject_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description",length = 1000)
    private String description;

    @OneToMany(mappedBy = "id")
    private List<Topic> topics;
}
