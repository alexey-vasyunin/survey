package ru.vasyunin.interview.survey.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.vasyunin.interview.survey.dto.QuestionDto;
import ru.vasyunin.interview.survey.service.QuestionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Возвращает список вопросов в опросе
     * @param id Идентификатор опроса
     * @param page Номер страницы (по умолчанию 0)
     * @param size Вопросов на странице (по умолчанию 100)
     * @return Список объектов QuestionDto
     */
    @GetMapping("/{id}")
    public List<QuestionDto> getQuestions(@PathVariable("id") Long id,
                                          @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                          @RequestParam(name = "size", required = false, defaultValue = "100") int size){
        return questionService.getQuestionsBySurveyId(id, page, size);
    }

    /**
     * Добавляет вопрос к опросу
     * @param surveyId Идентификатор опроса
     * @param title Текст вопроса
     * @param order Порядок сортировки
     * @return Возвращает объект QuestionDto добавленного вопроса
     * @throws HttpClientErrorException Бросает исключение со статусом HttpStatus.BAD_REQUEST если в базе не найден опрос с идентификатором <b>surveyId</b>
     */
    @PostMapping("/")
    public QuestionDto addQuestion(@RequestParam(name = "serveyId") Long surveyId,
                            @RequestParam(name = "title") String title,
                            @RequestParam(name = "order", required = false, defaultValue = "0") int order){
        return questionService.addQuestion(surveyId, title, order);
    }

    /**
     * Удаляет вопрос
     * @param id Идентификатор вопроса в базе
     * @return Возвращает статус HttpStatus.OK в случае успеха и HttpStatus.BAD_REQUEST если вопрос не найден
     */
    @DeleteMapping("/")
    public ResponseEntity deleteQuestion(@RequestParam Long id){
        questionService.deleteQuestion(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Редактирование уже существующего вопроса
     * @param id Идентификатор редактируемого вопроса
     * @param surveyId Идентификатор опроса, к которому привязан вопрос
     * @param title  Текст вопроса
     * @param order Порядок сортировки
     * @return QuestionDto отредактированного вопроса
     */
    @PutMapping("/")
    public QuestionDto changeQuestion(@RequestParam Long id,
                                 @RequestParam(name = "serveyId") Long surveyId,
                                 @RequestParam(name = "title") String title,
                                 @RequestParam(name = "order", required = false) Optional<Integer> order){
        return questionService.editQuestion(id, surveyId, title, order.orElse(0));
    }

}
