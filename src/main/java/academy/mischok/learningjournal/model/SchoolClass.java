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
public class SchoolClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "school_class_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "long_description",length = 1000)
    private String longDescription;

    @Column(name = "short_description", length = 100)
    private String shortDescription;

    @OneToMany(mappedBy = "id")
    private List<UserEntity> students;

    @OneToMany(mappedBy = "id")
    private List<ScheduleEntry> schedule;

}
