package academy.mischok.learningjournal.service;

import academy.mischok.learningjournal.repository.SchoolClassRepository;
import academy.mischok.learningjournal.repository.SubjectRepository;
import academy.mischok.learningjournal.repository.TopicRepository;
import academy.mischok.learningjournal.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class UserService {
 private final UserRepository userRepository;
 private final SchoolClassRepository schoolClassRepository;
 private final SubjectRepository subjectRepository;
 private final TopicRepository topicRepository;

    public UserService(UserRepository userRepository, SchoolClassRepository schoolClassRepository, SubjectRepository subjectRepository, TopicRepository topicRepository) {
        this.userRepository = userRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.subjectRepository = subjectRepository;
        this.topicRepository = topicRepository;
    }
}
