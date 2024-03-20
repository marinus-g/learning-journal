package academy.mischok.learningjournal.controller;

import academy.mischok.learningjournal.config.LearningJournalConfiguration;
import academy.mischok.learningjournal.dto.PasswordChangeDto;
import academy.mischok.learningjournal.dto.UserDto;
import academy.mischok.learningjournal.model.Role;
import academy.mischok.learningjournal.model.UserEntity;
import academy.mischok.learningjournal.repository.UserRepository;
import academy.mischok.learningjournal.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Objects;
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

    @GetMapping("/benutzerverwaltung")
    public String getUserManagement(Model model, @AuthenticationPrincipal UserEntity user, HttpServletRequest request) {
            model.addAttribute("selfUser", this.userService.toDto(user));
            model.addAttribute("httpServletRequest", request);
            model.addAttribute("users", this.userService.findAllUsers());
        return "usermanagement";
    }
    @GetMapping("/edit/{id}")
    public String openEditUser(@PathVariable Long id, Model model, @AuthenticationPrincipal UserEntity user, HttpServletRequest request) {
        model.addAttribute("selfUser", this.userService.toDto(user));
        model.addAttribute("httpServletRequest", request);
        model.addAttribute("user", this.userService.findUserById(id).map(userService::toDto));
        return "edit-user";
    }

    @PostMapping("/edit/{id}")
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

    @PostMapping(value = "/password")
    public String changePassword(@AuthenticationPrincipal UserEntity user, PasswordChangeDto passwordChangeDto) {
        return Optional.of(this.userService.changePassword(passwordChangeDto, user.getId()))
                .map(bool -> "redirect:/profile" + "?passwordAction=" + bool.name())
                .orElseThrow(()
                        -> new RuntimeException("An error occurred while changing password of user " + user.getId()));
    }

    // redirect:/profile
    @DeleteMapping(value = "/{id}/delete") // /user/1/delete?redirect=profile
    public String deleteUser(@AuthenticationPrincipal UserEntity selfUser, @PathVariable(value = "id") Long id,
                             @RequestParam(name = "redirect", required = false, defaultValue = "profile") String redirectUrl) {
        return Optional.of(id)
                .filter(aLong -> selfUser.getId().equals(id) || selfUser.getRoles().contains(Role.ADMIN))
                .map(aLong -> this.userService.deleteUser(id))
                .map(aBoolean -> {
                    if (selfUser.getId().equals(id)) {
                        return "redirect:/logout";
                    } else {
                        return "redirect:/" + redirectUrl + "?userDeleteSuccess=" + aBoolean;
                    }
                })
                .orElse("redirect:/" + redirectUrl + "?userDeleteSuccess=" + false);
    }
}