package academy.mischok.learningjournal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JournalEntryDto {

    private Long subject;
    private Long topic;
    private String title;
    private String entry;
    private List<Long> tag;

}