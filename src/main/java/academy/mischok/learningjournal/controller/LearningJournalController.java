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
@RequestMapping("/journal")
public class LearningJournalController {

    private final UserService userService;

    @Autowired
    public LearningJournalController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String journalsPage(@AuthenticationPrincipal UserEntity user, HttpServletRequest request, Model model) {
        model.addAttribute("httpServletRequest", request);
        model.addAttribute("selfUser", this.userService.toDto(user));
        return "journal";
    }
}
