package academy.mischok.learningjournal.controller;

import academy.mischok.learningjournal.model.UserEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DashboardController {

    @GetMapping(value = {"/dashboard", "/"})
    public String dashboard(@AuthenticationPrincipal UserEntity user, Model model) {
        return this.buildDashboard(user, model);
    }

    @PostMapping("/dashboard")
    public String dashboardPost(@AuthenticationPrincipal UserEntity user, Model model) {
        return this.buildDashboard(user, model);
    }

    private String buildDashboard(UserEntity userModel, Model model) {
        model.addAttribute("user", userModel);
        return "dashboard";
    }
}