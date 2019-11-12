package ru.vasyunin.interview.survey.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vasyunin.interview.survey.dto.SurveyDto;
import ru.vasyunin.interview.survey.service.SurveyService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/survey")
public class SurveyController {

    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    /**
     * Список существующих опросов (постаничный вывод)
     * @param page Номер страницы
     * @param size Размер страницы
     * @return Список объектов SurveyDto
     */
    @GetMapping("/")
    public List<SurveyDto> getSurveys(@RequestParam(name = "name", required = false) String name,
                                      @RequestParam(name = "date", required = false) LocalDate date,
                                      @RequestParam(name = "active", required = false) Boolean active,
                                      @RequestParam(name = "sort") int sort,
                                      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                      @RequestParam(name = "size", required = false, defaultValue = "100") int size){
        return surveyService.getAllSurveys(page, size);
    }

    /**
     * Добавление опроса
     * @param name       Название опроса
     * @param dateStart  Дата начала
     * @param dateFinish Дата конца
     * @param isActive   Актуальность опроса
     * @return Возвращает объект SurveyDto созданного опроса
     */
    @PostMapping("/")
    public SurveyDto addSurvey(@RequestParam(name = "name") String name,
                               @RequestParam(name = "dateStart") LocalDate dateStart,
                               @RequestParam(name = "dateFinish") LocalDate dateFinish,
                               @RequestParam(name = "active", required = false, defaultValue = "0") boolean isActive){
        return surveyService.addSurvey(name, dateStart, dateFinish, isActive);
    }

    /**
     * Удаление опроса
     * @param id Идентификатор опроса
     * @return Возвращает статус HttpStatus.OK в случае успеха и HttpStatus.BAD_REQUEST если опрос не найден
     */
    @DeleteMapping("/")
    public ResponseEntity deleteSurvey(@RequestParam Long id){
        surveyService.deleteSurvey(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Редактирование опроса
     * @param id Идентификатор опроса
     * @param name Название опроса
     * @param dateStart Дата старта
     * @param dateFinish Дата окончания
     * @param isActive Флаг актуальности
     * @return Возвращает SurveyDto измененного опроса
     */
    @PutMapping("/")
    public SurveyDto changeSurvey(@RequestParam Long id,
                               @RequestParam(name = "name") String name,
                               @RequestParam(name = "dateStart") LocalDate dateStart,
                               @RequestParam(name = "dateFinish") LocalDate dateFinish,
                               @RequestParam(name = "active", required = false, defaultValue = "0") boolean isActive){

        return surveyService.changeSurvey(id, name, dateStart, dateFinish, isActive);
    }

}
