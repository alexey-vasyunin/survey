package ru.vasyunin.interview.survey.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Entity
@Data
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

    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy("ordering ASC")
    private List<Question> questions;
}
