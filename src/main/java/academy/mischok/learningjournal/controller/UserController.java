package academy.mischok.learningjournal.controller;

import academy.mischok.learningjournal.config.LearningJournalConfiguration;
import academy.mischok.learningjournal.dto.UserDto;
import academy.mischok.learningjournal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final LearningJournalConfiguration configuration;

    @Autowired
    public UserController(UserService userService, LearningJournalConfiguration configuration) {
        this.userService = userService;
        this.configuration = configuration;
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable Long id,
                            UserDto userDto,
                           @RequestParam(defaultValue = "profile") String oldPath) {
        this.userService.updateUser(id, userDto);
        return "redirect:/" + oldPath;
    }

    @PostMapping("/{id}/avatar")
    public String changeAvatar(@PathVariable Long id, @RequestBody @RequestParam("file") MultipartFile picture,
                               @RequestParam(defaultValue = "profile") String oldPath) {
        this.userService.changeAvatar(id, picture);
        return "redirect:/" + oldPath;
    }

    @GetMapping(value = "/{id}/avatar", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getAvatar(@PathVariable Long id) {
        try {
            return Optional.of(this.userService.findUserAvatar(id)
                    .orElse(this.configuration.getUserImage().getContentAsByteArray()))
                    .map(picture -> {
                        final HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.valueOf("image/jpg"));
                        return ResponseEntity.ok().headers(headers).body(picture);
                    })
                    .orElseThrow(() -> new RuntimeException("Could not build response entity for avatar"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}