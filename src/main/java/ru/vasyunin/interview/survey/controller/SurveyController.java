package ru.vasyunin.interview.survey.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vasyunin.interview.survey.dto.SurveyDto;
import ru.vasyunin.interview.survey.service.SurveyService;
import ru.vasyunin.interview.survey.specs.SearchCriteria;
import ru.vasyunin.interview.survey.specs.SurveySpec;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/survey")
@Api(description = "Controller for working with surveys")
public class SurveyController {

    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    /**
     * Список существующих опросов (постаничный вывод)
     *
     * @param name   Фильтр по имени опроса (опционально)
     * @param date   Фильтр по дате - попадание в промежуток между началом и концом
     * @param active Фильтр по статусу опроса
     * @param sort   Поле для сортировки: 1 - по названию опроса; 2 - по дате начала
     * @param page   Номер страницы
     * @param size   Размер страницы
     * @return Список объектов SurveyDto
     */
    @GetMapping("/")
    public List<SurveyDto> getSurveys(
            @ApiParam("Фильтр по имени опроса (опционально)")
            @RequestParam(name = "name", required = false) String name,
            @ApiParam("Фильтр по дате - попадание в промежуток между началом и концом включительно")
            @RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @ApiParam("Фильтр по статусу опроса")
            @RequestParam(name = "active", required = false) Boolean active,
            @ApiParam("Поле для сортировки: 1 - по названию опроса; 2 - по дате начала")
            @RequestParam(name = "sort") int sort,
            @ApiParam("Номер страницы")
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @ApiParam("Размер страницы")
            @RequestParam(name = "size", required = false, defaultValue = "100") int size) {

        SurveySpec specName = new SurveySpec(new SearchCriteria("name", ":", name));
        SurveySpec specStart = new SurveySpec(new SearchCriteria("dateStart", "<", date));
        SurveySpec specFinish = new SurveySpec(new SearchCriteria("dateFinish", ">", date));
        SurveySpec specActive = new SurveySpec(new SearchCriteria("isActive", ":", active));
        return surveyService.getAllSurveys(specName.and(specActive).and(specStart).and(specFinish), page, size, sort);
    }

    /**
     * Добавление опроса
     *
     * @param name       Название опроса
     * @param dateStart  Дата начала
     * @param dateFinish Дата конца
     * @param isActive   Актуальность опроса
     * @return Возвращает объект SurveyDto созданного опроса
     */
    @PostMapping("/")
    public SurveyDto addSurvey(
            @ApiParam("Название опроса")
            @RequestParam(name = "name") String name,
            @ApiParam("Дата начала")
            @RequestParam(name = "dateStart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @ApiParam("Дата конца")
            @RequestParam(name = "dateFinish") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFinish,
            @ApiParam("Актуальность опроса")
            @RequestParam(name = "active", required = false, defaultValue = "0") boolean isActive) {
        return surveyService.addSurvey(name, dateStart, dateFinish, isActive);
    }

    /**
     * Удаление опроса
     *
     * @param id Идентификатор опроса
     * @return Возвращает статус HttpStatus.OK в случае успеха и HttpStatus.BAD_REQUEST если опрос не найден
     */
    @DeleteMapping("/")
    public ResponseEntity deleteSurvey(@ApiParam("Идентификатор опроса")
                                       @RequestParam Long id) {
        surveyService.deleteSurvey(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Редактирование опроса
     *
     * @param id         Идентификатор опроса
     * @param name       Название опроса
     * @param dateStart  Дата старта
     * @param dateFinish Дата окончания
     * @param isActive   Флаг актуальности
     * @return Возвращает SurveyDto измененного опроса
     */
    @PutMapping("/")
    public SurveyDto changeSurvey(
            @ApiParam("Идентификатор опроса")
            @RequestParam Long id,
            @ApiParam("Название опроса")
            @RequestParam(name = "name") String name,
            @ApiParam("Дата старта")
            @RequestParam(name = "dateStart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
            @ApiParam("Дата окончания")
            @RequestParam(name = "dateFinish") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFinish,
            @ApiParam("Флаг актуальности")
            @RequestParam(name = "active", required = false, defaultValue = "0") boolean isActive) {

        return surveyService.changeSurvey(id, name, dateStart, dateFinish, isActive);
    }

}
