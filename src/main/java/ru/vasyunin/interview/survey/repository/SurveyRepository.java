package ru.vasyunin.interview.survey.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vasyunin.interview.survey.dto.SurveyDto;
import ru.vasyunin.interview.survey.entity.Survey;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<SurveyDto> findAllBy(Pageable page);
}
