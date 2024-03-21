package academy.mischok.learningjournal.dto;

import lombok.*;

@Data
public class PasswordChangeDto {
    private String oldPassword;
    private String password;
}
