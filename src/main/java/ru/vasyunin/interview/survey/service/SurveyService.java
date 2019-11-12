package ru.vasyunin.interview.survey.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.vasyunin.interview.survey.dto.SurveyDto;
import ru.vasyunin.interview.survey.entity.Survey;
import ru.vasyunin.interview.survey.repository.SurveyRepository;
import ru.vasyunin.interview.survey.specs.SurveySpec;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * @param sortType Сортировка по: 1 - имени, 2 - дате начала
     * @return Список объектов SurveyDto
     */
    public List<SurveyDto> getAllSurveys(Specification<Survey> spec, int page, int size, int sortType){
        Sort sort;
        switch (sortType){
            case 1: sort = Sort.by("name"); break;
            case 2: sort = Sort.by("dateStart"); break;
            default: sort = Sort.by("name");
        }
        return surveyRepository.findAll(spec, PageRequest.of(page, size, sort))
                .stream()
                .map(SurveyDto::new)
                .collect(Collectors.toList());
    }


    /**
     * Добавляет опрос
     * @param name Название опроса
     * @param start    Дата начала
     * @param finish   Дата конца
     * @param isActual Флаг актуальности опроса
     * @return Возвращает объект SurveyDto созданного опроса
     */
    public SurveyDto addSurvey(String name, LocalDate start, LocalDate finish, boolean isActual){
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
    public SurveyDto changeSurvey(long id, String name, LocalDate start, LocalDate finish, boolean active) {
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
