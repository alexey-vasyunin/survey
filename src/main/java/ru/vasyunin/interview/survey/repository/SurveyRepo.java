package ru.vasyunin.interview.survey.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.vasyunin.interview.survey.entity.Survey;

public interface SurveyRepo extends JpaRepository<Survey, Long> {
}
