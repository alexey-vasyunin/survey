package ru.vasyunin.interview.survey.dto;

import lombok.Data;
import ru.vasyunin.interview.survey.entity.Question;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SurveyDto {
    private long id;
    private String name;
    private LocalDateTime dateStart;
    private LocalDateTime dateFinish;
    private boolean isActive;
    private List<Question> questions;
}
