package ru.vasyunin.interview.survey.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.vasyunin.interview.survey.dto.SurveyDto;
import ru.vasyunin.interview.survey.entity.Survey;
import ru.vasyunin.interview.survey.repository.SurveyRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {
    private final SurveyRepository surveyRepository;

    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    /**
     * Возвращает список опросов (постранично)
     * @param page Номер страницы
     * @param size Размер страницы
     * @return Список объектов SurveyDto
     */
    public List<SurveyDto> getAllSurveys(int page, int size){
        return surveyRepository.findAllBy(PageRequest.of(page, size));
    }


    /**
     * Добавляет опрос
     * @param name Название опроса
     * @param start    Дата начала
     * @param finish   Дата конца
     * @param isActual Флаг актуальности опроса
     * @return Возвращает объект SurveyDto созданного опроса
     */
    public SurveyDto addSurvey(String name, LocalDateTime start, LocalDateTime finish, boolean isActual){
        Survey survey = new Survey(name, start, finish, isActual);
        survey = surveyRepository.save(survey);
        return new SurveyDto(survey);
    }

    /**
     * Удаление опроса
     * @param id Идентификатор опроса
     */
    public void deleteSurvey(long id) {
        surveyRepository.deleteById(id);
    }

    /**
     * Изменение параметров опроса
     * @param id     Идентификатор опроса (не меняется)
     * @param name   Название опроса
     * @param start  Дата начала
     * @param finish Дата конца
     * @param active Флаг актуальности опроса
     * @return Возвращает объект SurveyDto измененного опроса
     */
    public SurveyDto changeSurvey(long id, String name, LocalDateTime start, LocalDateTime finish, boolean active) {
        Optional<Survey> survey = surveyRepository.findById(id);
        if (!survey.isPresent())
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Survey not found");

        Survey s = survey.get();
        s.setName(name);
        s.setDateStart(start);
        s.setDateFinish(finish);
        s.setActive(active);
        return new SurveyDto(s);
    }
}
