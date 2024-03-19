package academy.mischok.learningjournal.service;

import academy.mischok.learningjournal.dto.PasswordChangeDto;
import academy.mischok.learningjournal.dto.UserDto;
import academy.mischok.learningjournal.model.*;
import academy.mischok.learningjournal.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;
    private final UserRepository userRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;
    private final TopicRepository topicRepository;
    private final ScheduleEntriesRepository scheduleEntriesRepository;
    private final RandomLightningTopicRepository randomLightningTopicRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, SchoolClassRepository schoolClassRepository,
                       SubjectRepository subjectRepository, TopicRepository topicRepository, ScheduleEntriesRepository scheduleEntriesRepository, RandomLightningTopicRepository randomLightningTopicRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.subjectRepository = subjectRepository;
        this.topicRepository = topicRepository;
        this.scheduleEntriesRepository = scheduleEntriesRepository;
        this.randomLightningTopicRepository = randomLightningTopicRepository;
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
                    Optional.ofNullable(userDto.getRoles()).ifPresentOrElse(userEntity::setRoles,
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
                .roles(userRoles)
                .scheduleEntries(scheduleEntries)
                .teachingSubjects(teachingSubjects)
                .teachingTopics(teachingTopics)
                .randomLightningTopics(randomLightningTopics)
                .build();
    }

    @Transactional
    public UserEntity fromDto(UserDto userDto) { // null
        UserEntity userEntity = new UserEntity();
        if (i != 0) { // TODO: FIND A BETTER SOLUTION FOR THAT
            if (!entityManager.contains(userDto)) {
                userEntity = this.userRepository.findById(userDto.getId()).orElseThrow();
            }
        } else i++;

        final SchoolClass schoolClass = Optional.ofNullable(userDto.getSchoolClass())
                .flatMap(schoolClassRepository::findById)
                .orElse(null);

        final Set<Role> userRoles = userDto.getRoles();

        final List<ScheduleEntry> scheduleEntries = Optional.ofNullable(userDto.getScheduleEntries())
                .stream()
                .flatMap(Collection::stream)
                .map(scheduleEntriesRepository::findById)
                .map(scheduleEntry -> scheduleEntry.orElse(null))
                .filter(Objects::nonNull)
                .toList();
        final Set<Subject> teachingSubjects = Optional.ofNullable(userDto.getTeachingSubjects())
                .stream()
                .flatMap(Collection::stream)
                .map(subjectRepository::findById)
                .map(scheduleEntry -> scheduleEntry.orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        final Set<Topic> teachingTopics = Optional.ofNullable(userDto.getTeachingTopics())
                .stream()
                .flatMap(Collection::stream)
                .map(topicRepository::findById)
                .map(scheduleEntry -> scheduleEntry.orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        final var randomLightningTopics = Optional.ofNullable(userDto.getRandomLightningTopics())
                .stream()
                .flatMap(Collection::stream)
                .map(randomLightningTopicRepository::findById)
                .map(scheduleEntry -> scheduleEntry.orElse(null))
                .filter(Objects::nonNull)
                .toList();
        return UserEntity.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .schoolClass(schoolClass)
                .roles(userRoles)
                .scheduleEntries(scheduleEntries)
                .teachingSubjects(teachingSubjects)
                .teachingTopics(teachingTopics)
                .randomLightningTopics(randomLightningTopics)
                .build();
    }

    public Boolean changePassword(PasswordChangeDto pwDto, Long userId) {
        return this.userRepository.findById(userId)
                .filter(user -> passwordEncoder.matches(pwDto.getOldPassword(), user.getPassword()))
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(pwDto.getPassword()));
                    return userRepository.save(user);
                }).isPresent();
    }

    public List<UserEntity> findAllTeachersByTopic(Topic topic) {
        return userRepository.findAllByTopic(topic);
    }

    public List<UserEntity> findAllTeachersBySubject(Subject subject) {
        return userRepository.findAllBySubject(subject);
    }

    public Boolean addToSchoolClass(Long userId, Long schoolClassId) {
        userRepository.findById(userId)
                .ifPresent(user -> schoolClassRepository
                        .findById(schoolClassId)
                        .ifPresent(user::setSchoolClass));
        return true;
    }

    public Boolean leaveSchoolClass(UserEntity user) {
        if (user.getSchoolClass() == null) {
            return false;
        }
        user.setSchoolClass(null);
        return true;
    }
}