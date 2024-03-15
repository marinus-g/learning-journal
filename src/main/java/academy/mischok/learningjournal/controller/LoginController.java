package academy.mischok.learningjournal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {


    @GetMapping("/login")
    public String loginPage() {
        return "login";
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