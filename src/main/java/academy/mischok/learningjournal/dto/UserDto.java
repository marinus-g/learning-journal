package academy.mischok.learningjournal.dto;

import academy.mischok.learningjournal.model.RandomLightningTopic;
import academy.mischok.learningjournal.model.Role;
import academy.mischok.learningjournal.model.Topic;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.security.auth.Subject;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;

    private Long schoolClass;
    private Set<Role> userRoles;
    private List<Long> scheduleEntries;
    private List<Long> teachingSubjects;
    private List<Long> teachingTopics;
    private List<Long> randomLightningTopics;


    @Getter
    @Setter
    public static class CreateDto extends UserDto {

        private String password;

        public static CreateDto of(@NonNull String username, @NonNull String email,
                                   @NonNull String password, @NonNull String firstName, @NonNull String lastName) {
            CreateDto createDto = new CreateDto();
            createDto.setUserName(username);
            createDto.setPassword(password);
            createDto.setEmail(email);
            createDto.setFirstName(firstName);
            createDto.setLastName(lastName);
            return createDto;
        }
    }
}