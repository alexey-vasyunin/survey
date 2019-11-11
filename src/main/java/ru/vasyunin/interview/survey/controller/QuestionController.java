package ru.vasyunin.interview.survey.controller;

import org.springframework.web.bind.annotation.*;
import ru.vasyunin.interview.survey.dto.QuestionDto;
import ru.vasyunin.interview.survey.dto.SurveyDto;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @GetMapping("/")
    public List<QuestionDto> getQuestions(@RequestParam Long id){
        return Collections.emptyList();
    }

    @PostMapping("/")
    public String addQuestion(@RequestParam QuestionDto questionDto, @RequestParam Long surveyId){
        return "";
    }

    @DeleteMapping("/")
    public String deleteQuestion(@RequestParam Long id){
        return "";
    }

    @PutMapping("/")
    public String changeQuestion(@RequestParam Long id){
        return "";
    }

}
