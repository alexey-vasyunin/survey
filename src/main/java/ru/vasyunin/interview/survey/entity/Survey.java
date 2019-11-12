package ru.vasyunin.interview.survey.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "surveys")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_start")
    private LocalDate dateStart;

    @Column(name = "date_finish")
    private LocalDate dateFinish;

    @Column(name = "active")
    private boolean isActive;

    @OneToMany(mappedBy = "survey", fetch = FetchType.LAZY)
//    @OrderBy("ordering ASC")
    private List<Question> questions;

    public Survey(String name, LocalDate dateStart, LocalDate dateFinish, boolean isActive) {
        this.name = name;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.isActive = isActive;
    }
}
