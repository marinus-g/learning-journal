package academy.mischok.learningjournal.controller;

import academy.mischok.learningjournal.dto.SubjectDto;
import academy.mischok.learningjournal.model.UserEntity;
import academy.mischok.learningjournal.service.SubjectService;
import academy.mischok.learningjournal.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public String getSubjectPage(@AuthenticationPrincipal UserEntity user,
                                 @RequestParam(name = "error", required = false) Boolean error,
                                 HttpServletRequest httpServletRequest,
                                 Model model) {

        model.addAttribute("selfUser", user);
        model.addAttribute("httpServletRequest", httpServletRequest);
        model.addAttribute("newSubject", new SubjectDto());
        model.addAttribute("error", error);
        model.addAttribute("subjects", subjectService.getAllSubjects());
        System.out.println("error = " + error);
        return "subjects";
    }

    @PostMapping
    public String createSubject(@AuthenticationPrincipal UserEntity user,
                                @ModelAttribute SubjectDto subjectDto) {
        return "redirect:/subjects?error=" + !subjectService.createSubject(subjectDto);
    }
}