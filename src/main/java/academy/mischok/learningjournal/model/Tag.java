package academy.mischok.learningjournal.model;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Tag {

    @Column(name = "id")
    private Long id;

    @Column(name = "user")
    private UserEntity user;

}
