package academy.mischok.learningjournal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectDto {

    private Long id;
    private String name;
    private String description;
    private List<Long> topicIds;
    private String shortenedDescription;

}