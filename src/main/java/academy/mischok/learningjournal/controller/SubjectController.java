package academy.mischok.learningjournal.controller;

import academy.mischok.learningjournal.dto.SubjectDto;
import academy.mischok.learningjournal.dto.TopicDto;
import academy.mischok.learningjournal.model.UserEntity;
import academy.mischok.learningjournal.service.SubjectService;
import academy.mischok.learningjournal.service.TopicService;
import academy.mischok.learningjournal.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;
    private final TopicService topicService;

    public SubjectController(SubjectService subjectService, TopicService topicService) {
        this.subjectService = subjectService;
        this.topicService = topicService;
    }

    @GetMapping
    public String getSubjectPage(@AuthenticationPrincipal UserEntity user,
                                 @RequestParam(name = "error", required = false) Boolean error,
                                 @RequestParam(name = "topicError", required = false) Boolean topicError,
                                 HttpServletRequest httpServletRequest,
                                 Model model) {

        model.addAttribute("selfUser", user);
        model.addAttribute("httpServletRequest", httpServletRequest);
        model.addAttribute("newSubject", new SubjectDto());
        model.addAttribute("error", error);
        model.addAttribute("topicError", topicError);
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("subjectsJson", subjectService.toJson(subjectService.getAllSubjects()));
        return "subjects";
    }

    @PostMapping
    public String createSubject(@AuthenticationPrincipal UserEntity user,
                                @ModelAttribute SubjectDto subjectDto) {
        return "redirect:/subjects?topicError=" + !subjectService.createSubject(subjectDto);
    }

    @GetMapping("/{id}/topics")
    public String getTopicsFragment(@PathVariable Long id, Model model) {
        model.addAttribute("subjectId", id);
        model.addAttribute("newTopic", new TopicDto());
        model.addAttribute("topics", this.subjectService.findSubjectById(id)
                        .orElseThrow(() -> new NullPointerException("Subjet with id " + id + " not found!"))
                        .getTopics()
                        .stream()
                .map(this.topicService::toDto)
                .toList());
        return "components/topics";
    }

    @PostMapping("/{id}/topic")
    public String createTopic(@PathVariable(name = "id") Long subjectId, @ModelAttribute TopicDto topicDto) {
        return "redirect:/subjects?topicError=" + !this.topicService.createTopic(subjectId, topicDto);
    }
}