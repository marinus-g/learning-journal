package academy.mischok.learningjournal.model;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user")
    private UserEntity user;

}
