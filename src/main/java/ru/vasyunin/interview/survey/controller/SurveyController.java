package ru.vasyunin.interview.survey.controller;

import org.springframework.web.bind.annotation.*;
import ru.vasyunin.interview.survey.dto.SurveyDto;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/survey")
public class SurveyController {

    @GetMapping("/")
    public List<SurveyDto> getSurveys(){
        return Collections.emptyList();
    }

    @PostMapping("/")
    public String addSurvey(@RequestParam SurveyDto surveyDto){
        return "";
    }

    @DeleteMapping("/")
    public String deleteSurvey(@RequestParam Long id){
        return "";
    }

    @PutMapping("/")
    public String changeSurvey(@RequestParam Long id){
        return "";
    }

}
