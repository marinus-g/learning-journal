package academy.mischok.learningjournal.service;

import academy.mischok.learningjournal.dto.UserDto;
import academy.mischok.learningjournal.model.UserEntity;
import academy.mischok.learningjournal.repository.SchoolClassRepository;
import academy.mischok.learningjournal.repository.SubjectRepository;
import academy.mischok.learningjournal.repository.TopicRepository;
import academy.mischok.learningjournal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;
    private final TopicRepository topicRepository;

    @Autowired
    public UserService(UserRepository userRepository, SchoolClassRepository schoolClassRepository, SubjectRepository subjectRepository, TopicRepository topicRepository) {
        this.userRepository = userRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.subjectRepository = subjectRepository;
        this.topicRepository = topicRepository;
    }

    public Optional<UserEntity> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<UserEntity> findUserByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    public Optional<UserEntity> findUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    public Optional<UserEntity> findUserByLogin(String login) {
        return userRepository.findByUsernameIgnoreCase(login)
                .or(() -> userRepository.findByEmailIgnoreCase(login));
    }

    public Optional<UserDto> createUser(UserDto userDto) {
        UserEntity user = new UserEntity();
        user = userRepository.save(user);
        UserDto newUserDto = new UserDto();
        return Optional.of(newUserDto);
    }

    public boolean deleteUser(UserEntity user) {
        userRepository.deleteById(user.getId());
        return !userRepository.existsById(user.getId());
    }

    public void updateUser(UserDto userDto) {

        userRepository.findById(userDto.getId()).ifPresentOrElse(
                user -> {
                    if (userDto.getFirstName() != null) {
                        user.setFirstName(userDto.getFirstName());
                    }
                    if (userDto.getLastName() != null) {
                        user.setLastName(userDto.getLastName());
                    }
                    if (userDto.getUserName() != null) {
                        user.setUserName(userDto.getUserName());
                    }
                    if (userDto.getEmail() != null) {
                        user.setEmail(userDto.getEmail());
                    }
                    userRepository.save(user);
                },
                () -> {
                    throw new RuntimeException("User nicht gefunden");
                }
        );
    }

}
