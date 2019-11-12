package ru.vasyunin.interview.survey.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vasyunin.interview.survey.entity.Question;

@Data
@NoArgsConstructor
public class QuestionDto {
    private long id;
    private String title;

    public QuestionDto(Question question) {
        this.id = question.getId();
        this.title = question.getTitle();
    }
}
