package academy.mischok.learningjournal.service;

import academy.mischok.learningjournal.dto.PasswordChangeDto;
import academy.mischok.learningjournal.dto.UserDto;
import academy.mischok.learningjournal.model.RandomLightningTopic;
import academy.mischok.learningjournal.model.ScheduleEntry;
import academy.mischok.learningjournal.model.SchoolClass;
import academy.mischok.learningjournal.model.Subject;
import academy.mischok.learningjournal.model.Topic;
import academy.mischok.learningjournal.model.UserEntity;
import academy.mischok.learningjournal.repository.SchoolClassRepository;
import academy.mischok.learningjournal.repository.SubjectRepository;
import academy.mischok.learningjournal.repository.TopicRepository;
import academy.mischok.learningjournal.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
public class UserService  {

    @PersistenceContext
    private EntityManager entityManager;
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
                        .username(createDto.getUsername())
                        .email(createDto.getEmail())
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .firstName(createDto.getFirstName())
                        .lastName(createDto.getLastName())
                        .build()
                )
                .map(userEntity -> {
                    Optional.ofNullable(userDto.getUserRoles()).ifPresentOrElse(userEntity::setRoles,
                            () -> userEntity.setRoles(new HashSet<>()));
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
                    if (userDto.getUsername() != null) {
                        user.setUsername(userDto.getUsername());
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

    int i = 0;
    @Transactional
    public UserDto toDto(UserEntity userEntity) { // null
        /*
        Optional.ofNullable(userEntity.getScheduleEntries()).stream()
                .flatMap(scheduleEntries -> scheduleEntries.stream())
                .map(scheduleEntry -> scheduleEntry.getId())
                .forEach(aLong -> al);
         */


        if (i != 0) { // TODO: FIND A BETTER SOLUTION FOR THAT
            if (!entityManager.contains(userEntity)) {
                userEntity = this.userRepository.findById(userEntity.getId()).orElseThrow();
            }
        } else i++;



        final var schoolClass = Optional.ofNullable(userEntity.getSchoolClass())
                .map(SchoolClass::getId).orElse(null);
        final var userRoles = Optional.ofNullable(userEntity.getRoles())
                .orElseThrow(() -> new RuntimeException(new NullPointerException("User roles are null")));
        final var scheduleEntries = Optional.ofNullable(userEntity.getScheduleEntries())
                .stream()
                .flatMap(Collection::stream)
                .map(ScheduleEntry::getId)
                .toList();
        final var teachingSubjects = Optional.ofNullable(userEntity.getTeachingSubjects()).stream()
                .flatMap(Collection::stream)
                .map(Subject::getId)
                .toList();
        final var teachingTopics = Optional.ofNullable(userEntity.getTeachingTopics())
                .stream()
                .flatMap(Collection::stream)
                .map(Topic::getId)
                .toList();
        final var randomLightningTopics = Optional.ofNullable(userEntity.getRandomLightningTopics())
                .stream()
                .flatMap(Collection::stream)
                .map(RandomLightningTopic::getId)
                .toList();
        return UserDto.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .schoolClass(schoolClass)
                .userRoles(userRoles)
                .scheduleEntries(scheduleEntries)
                .teachingSubjects(teachingSubjects)
                .teachingTopics(teachingTopics)
                .randomLightningTopics(randomLightningTopics)
                .build();
    }

    /*   public Boolean changePassword(PasswordChangeDto pwDto, UserEntity user) {
           if (pwDto.getPassword().equals(pwDto.getOldPassword())) {
               return false;
           } else user.setPassword(pwDto.getPassword());
           userRepository.save(user);
       }
     */
    public Boolean changePassword(PasswordChangeDto pwDto, Long userId) {
        return this.userRepository.findById(userId)
                .filter(user -> passwordEncoder.matches(pwDto.getOldPassword(), user.getPassword()))
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(pwDto.getPassword()));
                    return userRepository.save(user);
                }).isPresent();


    }
}