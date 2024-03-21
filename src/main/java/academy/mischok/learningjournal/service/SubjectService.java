package academy.mischok.learningjournal.service;

import academy.mischok.learningjournal.dto.SubjectDto;
import academy.mischok.learningjournal.model.Subject;
import academy.mischok.learningjournal.model.Topic;
import academy.mischok.learningjournal.repository.SubjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Boolean createSubject(SubjectDto subjectDto) {
        return Optional.ofNullable(subjectDto)
                .filter(subjectDto1 -> !subjectRepository.existsByNameIgnoreCase(subjectDto1.getName()))
                .map(subjectDto1 -> Subject.builder()
                        .name(subjectDto1.getName())
                        .description(subjectDto1.getDescription())
                        .topics(new ArrayList<>())
                        .build())
                .map(subjectRepository::save)
                .map(subject -> subject.getId() != null)
                .orElse(false);
    }

    public SubjectDto toDto(Subject subject) {
        return new SubjectDto(
                subject.getId(),
                subject.getName(),
                subject.getDescription(),
                subject.getTopics().stream().map(Topic::getId).toList(),
                subject.getDescription().substring(0, Math.min(subject.getDescription().length(), 100))
        );
    }

    public String toJson(List<SubjectDto> subjects) { // THYMELEAF XD MOMENT
        try {
            return OBJECT_MAPPER.writeValueAsString(subjects);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<SubjectDto> getAllSubjects() {
        return subjectRepository.findAll().stream().map(this::toDto).toList();
    }

    public Optional<Subject> findSubjectById(Long id) {
        return subjectRepository.findById(id);
    }
}
