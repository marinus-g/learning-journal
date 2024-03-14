package academy.mischok.learningjournal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.security.auth.Subject;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ElementCollection
    @CollectionTable(name = "user_roles")
    private Set<Role> userRoles;

    @ManyToOne
    private SchoolClass schoolClass;

    @Column(name = "picture_id")
    private String pictureId;

    @OneToMany(mappedBy = "user_id")
    private List<ScheduleEntry> scheduleEntries;

    @Column(name = "teaching_subjects")
    private Set<Subject> teachingSubjects;

    @Column(name = "teaching_topics")
    private Set<Topic> teachingTopics;

    @OneToMany(mappedBy = "user_id")
    private List<RandomLightningTopic> randomLightningTopics;








}
