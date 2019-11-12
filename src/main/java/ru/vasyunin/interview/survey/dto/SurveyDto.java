package ru.vasyunin.interview.survey.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vasyunin.interview.survey.entity.Question;
import ru.vasyunin.interview.survey.entity.Survey;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class SurveyDto {
    private long id;
    private String name;
    private LocalDate dateStart;
    private LocalDate dateFinish;
    private boolean isActive;
    private List<Question> questions;

    public SurveyDto(Survey survey) {
        this.id = survey.getId();
        this.name = survey.getName();
        this.dateStart = survey.getDateStart();
        this.dateFinish = survey.getDateFinish();
        this.isActive = survey.isActive();
        this.questions = survey.getQuestions();
    }
}
