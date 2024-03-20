package academy.mischok.learningjournal.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopicDto {

    private Long id;
    private String name;
    private Long subject;

}
