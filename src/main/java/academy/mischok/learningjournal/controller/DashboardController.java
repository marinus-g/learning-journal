package academy.mischok.learningjournal.controller;

import academy.mischok.learningjournal.model.UserEntity;
import academy.mischok.learningjournal.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DashboardController {

    private final UserService userService;

    @Autowired
    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    @GetMapping(value = {"/dashboard", "/"})
    public String dashboard(@AuthenticationPrincipal UserEntity user, HttpServletRequest request,Model model) {
        return this.buildDashboard(user, request, model);
    }

    @Transactional
    @PostMapping("/dashboard")
    public String dashboardPost(@AuthenticationPrincipal UserEntity user, HttpServletRequest request, Model model) {
        return this.buildDashboard(user, request, model);
    }

    private String buildDashboard(UserEntity userEntity, HttpServletRequest request, Model model) {
        model.addAttribute("selfUser", this.userService.toDto(userEntity));
        model.addAttribute("httpServletRequest", request);
        return "dashboard";
    }
}