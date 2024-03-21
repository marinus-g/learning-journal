package academy.mischok.learningjournal.dto;

import academy.mischok.learningjournal.model.UserEntity;
import jakarta.persistence.Column;
import lombok.*;
import org.h2.engine.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolClassDto {

    private Long id;

    private LocalDate createDate;

    private String name;

    private String longDescription;

    private String shortDescription;


}
