package academy.mischok.learningjournal.service;

import academy.mischok.learningjournal.dto.UserDto;
import academy.mischok.learningjournal.model.*;
import academy.mischok.learningjournal.repository.SchoolClassRepository;
import academy.mischok.learningjournal.repository.SubjectRepository;
import academy.mischok.learningjournal.repository.TopicRepository;
import academy.mischok.learningjournal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
public class UserService  {
    private final UserRepository userRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;
    private final TopicRepository topicRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, SchoolClassRepository schoolClassRepository,
                       SubjectRepository subjectRepository, TopicRepository topicRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.subjectRepository = subjectRepository;
        this.topicRepository = topicRepository;
        this.passwordEncoder = passwordEncoder;
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

    public Optional<UserDto> createUser(UserDto.CreateDto userDto) {
        return Optional.ofNullable(userDto)
                .map(createDto -> UserEntity.builder()
                        .username(createDto.getUserName())
                        .email(createDto.getEmail())
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .firstName(createDto.getFirstName())
                        .lastName(createDto.getLastName())
                        .build()
                )
                .map(userEntity -> {
                    Optional.ofNullable(userDto.getUserRoles()).ifPresent(userEntity::setRoles);
                    return userEntity;
                })
                .map(this.userRepository::save)
                .map(this::toDto);
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
                        user.setUsername(userDto.getUserName());
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

    private UserDto toDto(UserEntity userEntity) { // null
        /*
        Optional.ofNullable(userEntity.getScheduleEntries()).stream()
                .flatMap(scheduleEntries -> scheduleEntries.stream())
                .map(scheduleEntry -> scheduleEntry.getId())
                .forEach(aLong -> al);
         */

        return UserDto.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .userName(userEntity.getUsername())
                .email(userEntity.getEmail())
                .schoolClass(Optional.ofNullable(userEntity.getSchoolClass()).map(SchoolClass::getId).orElse(null))
                .userRoles(Optional.ofNullable(userEntity.getRoles()).orElse(new HashSet<>()))
                .scheduleEntries(Optional.ofNullable(userEntity.getScheduleEntries()).stream().flatMap(Collection::stream).map(ScheduleEntry::getId).toList())
                .teachingSubjects(Optional.ofNullable(userEntity.getTeachingSubjects()).stream().flatMap(Collection::stream).map(Subject::getId).toList())
                .teachingTopics(Optional.ofNullable(userEntity.getTeachingTopics()).stream().flatMap(Collection::stream).map(Topic::getId).toList())
                .randomLightningTopics(Optional.ofNullable(userEntity.getRandomLightningTopics()).stream().flatMap(Collection::stream).map(RandomLightningTopic::getId).toList())
                .build();
    }
}
