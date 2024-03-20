package academy.mischok.learningjournal.service;

import academy.mischok.learningjournal.dto.TopicDto;
import academy.mischok.learningjournal.repository.TopicRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @SneakyThrows
    public String buildTopicJson() {
        final StringBuilder builder = new StringBuilder();
        List<TopicDto> testTopics = new ArrayList<>();
        testTopics.add(TopicDto.builder().id(1L).name("Algebra").subject(1L).build());
        testTopics.add(TopicDto.builder().id(2L).name("Gedichtanalyse").subject(2L).build());
        testTopics.add(TopicDto.builder().id(3L).name("Vokabeln").subject(3L).build());
        testTopics.stream().map(topic -> topic.getId() + "%" + topic.getName() + "%" + topic.getSubject())
                .forEach(s -> builder.append(s).append("#"));
        this.topicRepository.findAll()
                .stream().map(topic -> topic.getId() + "%" + topic.getName() + "%" + topic.getSubject().getId())
                .forEach(s -> builder.append(s).append("#"));
        return OBJECT_MAPPER.writeValueAsString(testTopics);
    }
}