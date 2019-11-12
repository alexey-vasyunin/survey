package ru.vasyunin.interview.survey.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vasyunin.interview.survey.dto.QuestionDto;
import ru.vasyunin.interview.survey.entity.Question;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<QuestionDto> findAllBySurveyIdOrderByOrder(Long id, Pageable page);
}
