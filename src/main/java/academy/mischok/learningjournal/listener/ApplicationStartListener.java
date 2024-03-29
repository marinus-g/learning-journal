package academy.mischok.learningjournal.listener;

import academy.mischok.learningjournal.config.LearningJournalConfiguration;
import academy.mischok.learningjournal.dto.UserDto;
import academy.mischok.learningjournal.model.Role;
import academy.mischok.learningjournal.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ApplicationStartListener implements ApplicationListener<ApplicationStartedEvent> {

    private final UserService userService;
    private final LearningJournalConfiguration learningJournalConfiguration;

    @Autowired
    public ApplicationStartListener(UserService userService, LearningJournalConfiguration learningJournalConfiguration) {
        this.userService = userService;
        this.learningJournalConfiguration = learningJournalConfiguration;
    }

    @Transactional
    @Override
    public void onApplicationEvent(@NonNull ApplicationStartedEvent event) {

        if (this.userService.findUserByUsername(learningJournalConfiguration.getDefaultAdminUsername()).isEmpty()) {
            UserDto.CreateDto createDto = UserDto.CreateDto.of(
                    learningJournalConfiguration.getDefaultAdminUsername(),
                    learningJournalConfiguration.getDefaultAdminEmail(),
                    learningJournalConfiguration.getDefaultAdminPassword(),
                    learningJournalConfiguration.getDefaultAdminUsername(),
                    learningJournalConfiguration.getDefaultAdminUsername());
            createDto.setRoles(Set.of(Role.ADMIN));
            userService.createUser(createDto)
                    .ifPresentOrElse(userDto -> System.out.println("Admin user created: " + userDto.getUsername()),
                            () -> System.out.println("Admin user creation failed"));
            UserDto.CreateDto createDto2 = UserDto.CreateDto.of(
                    "Desiree",
                    "desireehaglwimmer@gmail.com",
                    "abcdef",
                    "Desiree",
                    "Blechle");
            createDto2.setRoles(Set.of(Role.TEACHER));
            userService.createUser(createDto2)
                    .ifPresentOrElse(userDto -> System.out.println("user created: " + userDto.getUsername()),
                            () -> System.out.println("user creation failed"));
        }

    }
}
