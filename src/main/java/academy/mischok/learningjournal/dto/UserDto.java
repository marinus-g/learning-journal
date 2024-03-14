package academy.mischok.learningjournal.dto;

import academy.mischok.learningjournal.model.RandomLightningTopic;
import academy.mischok.learningjournal.model.Role;
import academy.mischok.learningjournal.model.ScheduleEntry;
import academy.mischok.learningjournal.model.Topic;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.OneToMany;
import lombok.Data;

import javax.security.auth.Subject;
import java.util.List;
import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private Long schoolClass;
    private Set<Role> userRoles;
    private List<Long> scheduleEntries;
    private Set<Subject> teachingSubjects;
    private Set<Topic> teachingTopics;
    private List<RandomLightningTopic> randomLightningTopics;
}
