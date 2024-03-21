package academy.mischok.learningjournal.controller;

import academy.mischok.learningjournal.dto.JournalEntryDto;
import academy.mischok.learningjournal.dto.SubjectDto;
import academy.mischok.learningjournal.dto.TopicDto;
import academy.mischok.learningjournal.model.UserEntity;
import academy.mischok.learningjournal.repository.SubjectRepository;
import academy.mischok.learningjournal.service.JournalService;
import academy.mischok.learningjournal.service.SubjectService;
import academy.mischok.learningjournal.service.TopicService;
import academy.mischok.learningjournal.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("journals")
public class JournalEntryController {

    private final UserService userService;
    private final JournalService journalService;
    private final TopicService topicService;
    private final SubjectService subjectService;

    @Autowired
    public JournalEntryController(UserService userService, JournalService journalService, TopicService topicService, SubjectService subjectService) {
        this.userService = userService;

        this.journalService = journalService;
        this.topicService = topicService;
        this.subjectService = subjectService;
    }

    @GetMapping
    public String journalEntries(@AuthenticationPrincipal UserEntity userEntity, Model model, HttpServletRequest request) {

        model.addAttribute("subjects", this.subjectService.getAllSubjects());
        model.addAttribute("journalEntries", this.journalService.getJournalEntriesByUser(userEntity.getId()));
        model.addAttribute("selfUser", this.userService.toDto(userEntity));
        model.addAttribute("httpServletRequest", request);
        model.addAttribute("newJournalEntry", new JournalEntryDto());
        System.out.println("TOPICJSON" + this.topicService.buildTopicJson());
        model.addAttribute("topics", this.topicService.buildTopicJson());
        return "journals";
    }

    @GetMapping("/{id}")
    public String showJournalEntry(@AuthenticationPrincipal UserEntity userEntity, @PathVariable Long id, Model model, HttpServletRequest request) {
        System.out.println("ID: " + id);
        final JournalEntryDto journalEntryDto = this.journalService.findJournalByIdAndUser(id, userEntity);
        model.addAttribute("journalEntry", journalEntryDto);
        model.addAttribute("subject", this.subjectService.findSubjectById(journalEntryDto.getSubject()).orElseThrow());
        model.addAttribute("topic", this.topicService.findTopicById(journalEntryDto.getTopic()).orElseThrow());
        model.addAttribute("selfUser", this.userService.toDto(userEntity));
        model.addAttribute("httpServletRequest", request);
        return "components/journal/view-journal :: journal";
    }

    @PostMapping
    public String createJournalEntry(@AuthenticationPrincipal UserEntity userEntity, JournalEntryDto journalEntryDto) {
        journalEntryDto.setTimestamp(Instant.now());
        journalEntryDto.setUser(userEntity.getId());
        journalService.createJournalEntry(journalEntryDto);
        return "redirect:/journals";
    }
}
