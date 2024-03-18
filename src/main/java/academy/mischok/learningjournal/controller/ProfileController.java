package academy.mischok.learningjournal.controller;

import academy.mischok.learningjournal.model.UserEntity;
import academy.mischok.learningjournal.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getProfilePage(@AuthenticationPrincipal UserEntity user, HttpServletRequest request, Model model) {
        model.addAttribute("selfUser", this.userService.toDto(user));
        model.addAttribute("httpServletRequest", request);
        return "profile";
    }
}
