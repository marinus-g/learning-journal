package academy.mischok.learningjournal.controller;

import academy.mischok.learningjournal.model.UserEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LoginController {


    @RequestMapping("/login")
    public String loginPage(@AuthenticationPrincipal UserEntity user, RedirectAttributes redirectAttributes) {
        return Optional.ofNullable(user).map(u -> "redirect:/dashboard").orElse("login");
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/login-post")
    public String loginPost(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("loginPost", true);
        return "redirect:/dashboard";
    }
}