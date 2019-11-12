package ru.vasyunin.interview.survey.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @Column(name = "survey_id", insertable = false, updatable = false)
    private Long surveyId;

    @Column(name = "title")
    private String title;

    @Column(name = "ordering")
    private Long order;

    public Question(Survey survey, String title, Long order) {
        this.survey = survey;
        this.title = title;
        this.order = order;
    }
}
