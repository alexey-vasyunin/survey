package ru.vasyunin.interview.survey.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

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
    private LocalDateTime dateStart;

    @Column(name = "date_finish")
    private LocalDateTime dateFinish;

    @Column(name = "active")
    private boolean isActive;

    @OneToMany(mappedBy = "survey", fetch = FetchType.LAZY)
//    @OrderBy("ordering ASC")
    private List<Question> questions;

    public Survey(String name, LocalDateTime dateStart, LocalDateTime dateFinish, boolean isActive) {
        this.name = name;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.isActive = isActive;
    }
}
