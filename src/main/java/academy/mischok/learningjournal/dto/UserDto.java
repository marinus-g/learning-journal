package academy.mischok.learningjournal.dto;

import academy.mischok.learningjournal.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

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
    private String username;
    private String email;

    private Long schoolClass;
    private Set<Role> roles;
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
            createDto.setUsername(username);
            createDto.setPassword(password);
            createDto.setEmail(email);
            createDto.setFirstName(firstName);
            createDto.setLastName(lastName);
            return createDto;
        }
    }
}