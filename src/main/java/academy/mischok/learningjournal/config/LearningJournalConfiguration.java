package academy.mischok.learningjournal.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class LearningJournalConfiguration {


    @Value("${learning-journal.default.user.name}")
    private String defaultAdminUsername;

    @Value("${learning-journal.default.user.password}")
    private String defaultAdminPassword;

    @Value("${learning-journal.default.user.email}")
    private String defaultAdminEmail;

}