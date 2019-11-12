package ru.vasyunin.interview.survey.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.vasyunin.interview.survey.dto.QuestionDto;
import ru.vasyunin.interview.survey.entity.Question;
import ru.vasyunin.interview.survey.entity.Survey;
import ru.vasyunin.interview.survey.repository.QuestionRepository;
import ru.vasyunin.interview.survey.repository.SurveyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final SurveyRepository surveyRepository;

    public QuestionService(QuestionRepository questionRepository, SurveyRepository surveyRepository) {
        this.questionRepository = questionRepository;
        this.surveyRepository = surveyRepository;
    }

    /**
     * Функция возвращает список вопросов у опроса в постраничном виде
     * @param surveyId Идентификатор опроса
     * @param page Номер страницы
     * @param size Размер страницы
     * @return Возвращает список вопросов отсортированный по полю ordering в БД
     */
    public List<QuestionDto> getQuestionsBySurveyId(long surveyId, int page, int size){
        return questionRepository.findAllBySurveyIdOrderByOrder(surveyId, PageRequest.of(page, size));
    }

    /**
     * Функуция добавляет вопрос к опросу
     * @param surveyId Идентификатор опроса
     * @param title Текст вопроса
     * @param order Порядок сортировки
     * @return Возвращает DTO-объект вопроса
     */
    public QuestionDto addQuestion(long surveyId, String title, long order){
        Optional<Survey> survey = surveyRepository.findById(surveyId);
        if (!survey.isPresent())
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);

        Question question = new Question(survey.get(), title, order);
        question = questionRepository.save(question);
        return new QuestionDto(question);
    }

    /**
     * Удаление вопроса
     * @param id Идентификатор вопроса
     */
    public void deleteQuestion(long id){
        if (!questionRepository.findById(id).isPresent())
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        questionRepository.deleteById(id);
    }

    /**
     * Редактирование вопроса
     * @param id Идентификатор вопроса
     * @param surveyId Идентификатор опроса
     * @param title Текст вопроса
     * @param order Порядок сортировки
     * @return Возвращает DTO-объект вопроса
     */
    public QuestionDto editQuestion(long id, long surveyId, String title, long order){
        Optional<Survey> survey = surveyRepository.findById(surveyId);
        if (!survey.isPresent())
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);

        Optional<Question> question = questionRepository.findById(id);
        if (!question.isPresent())
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);

        Question q = question.get();
        q.setSurvey(survey.get());
        q.setTitle(title);
        q.setOrder(order);
        return new QuestionDto(q);
    }}
