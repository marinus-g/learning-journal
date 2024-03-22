package academy.mischok.learningjournal.service;

import academy.mischok.learningjournal.dto.SchoolClassDto;
import academy.mischok.learningjournal.model.SchoolClass;
import academy.mischok.learningjournal.repository.SchoolClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class SchoolClassService {

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    public List<SchoolClassDto> findAllSchoolClasses(){

        List<SchoolClass> classList = schoolClassRepository.findAll();
        List<SchoolClassDto> classes = new ArrayList<>();

        for (SchoolClass myClass : classList) {
            SchoolClassDto dto = new SchoolClassDto();
            dto.setId(myClass.getId());
            dto.setName(myClass.getName());
            dto.setShortDescription(myClass.getShortDescription());
            dto.setLongDescription(myClass.getLongDescription());
            classes.add(dto);
        }

            return classes;

    }

    public boolean createSchoolClass(SchoolClassDto schoolClassDto) {
        if (schoolClassRepository.findByName(schoolClassDto.getName()).isPresent()) {
            return false;
        }
        SchoolClass newClass = new SchoolClass();
        newClass.setName(schoolClassDto.getName());
        newClass.setLongDescription(schoolClassDto.getLongDescription());
        newClass.setShortDescription(schoolClassDto.getShortDescription());
        schoolClassRepository.saveAndFlush(newClass);
            return true;
    }

    public SchoolClassDto catchSchoolClassById(Long classId) {
        SchoolClass classEntity=schoolClassRepository.findById(classId).orElseThrow();
        SchoolClassDto dto = new SchoolClassDto();
        dto.setId(classEntity.getId());
        dto.setName(classEntity.getName());
        dto.setShortDescription(classEntity.getShortDescription());
        dto.setLongDescription(classEntity.getLongDescription());

            return dto;
    }
}

