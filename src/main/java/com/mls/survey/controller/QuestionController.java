/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.controller;

import com.mls.survey.datatransferobject.QuestionDTO;
import com.mls.survey.datatransferobject.ResponseDistributionDTO;
import com.mls.survey.domainobject.Question;
import com.mls.survey.exception.ConstraintsViolationException;
import com.mls.survey.exception.EntityNotFoundException;
import static com.mls.survey.mapper.QuestionMapper.makeQuestion;
import static com.mls.survey.mapper.QuestionMapper.makeQuestionDTO;
import static com.mls.survey.mapper.QuestionMapper.makeQuestionDTOList;
import com.mls.survey.service.question.QuestionService;
import com.mls.survey.service.response.SurveyResponseService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@RestController
@RequestMapping("v1/questions")
public class QuestionController {
    
    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private SurveyResponseService surveyResponseService;
    
    /**
     * Create a new question
     * @param questionDto
     * @return
     * @throws ConstraintsViolationException 
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionDTO createQuestion(@Valid @RequestBody QuestionDTO questionDto) throws ConstraintsViolationException {
        Question question = makeQuestion(questionDto);
        return makeQuestionDTO(questionService.create(question));
    }
    
    /**
     * Update Question
     * @param id
     * @param questionDto
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException 
     */
    @PutMapping("{id}")
    public void updateQuestion(@PathVariable long id, @Valid @RequestBody QuestionDTO questionDto) throws ConstraintsViolationException, EntityNotFoundException {
        questionService.update(id, questionDto);
    }
    
    /**
     * Delete Question
     * @param id
     * @throws EntityNotFoundException 
     */
    @DeleteMapping("{id}")
    public void deleteQuestion(@PathVariable long id) throws EntityNotFoundException {
        questionService.delete(id);
    }
    
    /**
     * Get all questions
     * @return 
     */
    @GetMapping
    public List<QuestionDTO> getQuestions() {
        return makeQuestionDTOList(questionService.getQuestions());
    }
    
    /**
     * Get question
     * @param id
     * @return
     * @throws EntityNotFoundException 
     */
    @GetMapping("{id}")
    public QuestionDTO getQuestion(@PathVariable long id) throws EntityNotFoundException {
        return makeQuestionDTO(questionService.find(id));
    }
    
    /**
     * Get response to a question
     * @param id
     * @return
     * @throws EntityNotFoundException 
     */
    @GetMapping("/response/{id}")
    public ResponseDistributionDTO getResponses(@PathVariable long id) throws EntityNotFoundException {
        return surveyResponseService.getResponsesToQuestion(questionService.find(id));
    }
}
