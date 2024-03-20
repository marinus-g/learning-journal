package academy.mischok.learningjournal.service;

import academy.mischok.learningjournal.dto.TopicDto;
import academy.mischok.learningjournal.model.Subject;
import academy.mischok.learningjournal.model.Topic;
import academy.mischok.learningjournal.repository.SubjectRepository;
import academy.mischok.learningjournal.repository.TopicRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService {

    private final SubjectRepository subjectRepository;
    private final TopicRepository topicRepository;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    public TopicService(SubjectRepository subjectRepository, TopicRepository topicRepository) {
        this.subjectRepository = subjectRepository;
        this.topicRepository = topicRepository;
    }

    @SneakyThrows
    public String buildTopicJson() {
        return OBJECT_MAPPER.writeValueAsString(this.topicRepository.findAll()
                .stream()
                        .map(this::toDto)
                .toList());
    }

    public TopicDto toDto(Topic topic) {
        return TopicDto.builder()
                .id(topic.getId())
                .name(topic.getName())
                .subject(topic.getSubject().getId())
                .description(topic.getDescription())
                .build();
    }

   public boolean createTopic(Long subjectId, TopicDto topicDto) {
    Subject subject = this.subjectRepository.findById(subjectId).orElseThrow();
    Topic topic = Topic.builder()
            .name(topicDto.getName())
            .description(topicDto.getDescription())
            .subject(subject)
            .build();
    subject.getTopics().add(topic);
    this.subjectRepository.save(subject);
    this.topicRepository.save(topic);
    // Re-fetch the subject from the database to get the latest state
    subject = this.subjectRepository.findById(subjectId).orElseThrow();
    System.out.println("TOPICS:" + subject.getTopics().size());
    return topic.getId() != null;
}
}