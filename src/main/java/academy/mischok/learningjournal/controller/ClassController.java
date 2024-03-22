package academy.mischok.learningjournal.controller;

import academy.mischok.learningjournal.dto.SchoolClassDto;
import academy.mischok.learningjournal.dto.UserDto;
import academy.mischok.learningjournal.model.SchoolClass;
import academy.mischok.learningjournal.service.SchoolClassService;
import academy.mischok.learningjournal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ClassController {


    @Autowired
    private SchoolClassService schoolClassService;


    @GetMapping("/classrooms")
    public String getClassrooms(Model model){

        List<SchoolClassDto> classes = schoolClassService.findAllSchoolClasses();
        model.addAttribute("classes",classes);
        model.addAttribute("newClass",new SchoolClassDto());
        return "classes";
    }

    @PostMapping("/classrooms")
    public String createClass(@ModelAttribute("newClass") SchoolClassDto schoolClassDto,Model model) {
        model.addAttribute("createClass", schoolClassService.createSchoolClass(schoolClassDto));
        List<SchoolClassDto> classes = schoolClassService.findAllSchoolClasses();
        model.addAttribute("classes",classes);
        model.addAttribute("newClass",new SchoolClassDto());
        return "classes";


    }

    @GetMapping("/classrooms/{id}")
    public String classroom(@PathVariable Long id, Model model){
        return "Klasse";
    }

    @GetMapping("/classrooms/edit/{id}")
    public String getEditClassroom(@PathVariable("id") Long classId, Model model) {
        SchoolClassDto editDto = schoolClassService.catchSchoolClassById(classId);
        model.addAttribute("editClass",editDto);
        return "editClass";
    }

    }
