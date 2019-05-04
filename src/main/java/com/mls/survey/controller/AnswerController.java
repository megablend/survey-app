/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.controller;

import com.mls.survey.datatransferobject.AnswerDTO;
import com.mls.survey.domainobject.Answer;
import com.mls.survey.exception.ConstraintsViolationException;
import com.mls.survey.exception.EntityNotFoundException;
import com.mls.survey.mapper.AnswerMapper;
import static com.mls.survey.mapper.AnswerMapper.makeAnswer;
import com.mls.survey.service.answer.AnswerService;
import com.mls.survey.service.question.QuestionService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("v1/answers")
public class AnswerController {
    
    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private AnswerService answerService;
    
    /**
     * Create a new answer
     * @param questionId
     * @param answerDTO
     * @return
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException 
     */
    @PostMapping("{questionId}")
    @ResponseStatus(HttpStatus.CREATED)
    public AnswerDTO createAnswer(@PathVariable long questionId, @Valid @RequestBody AnswerDTO answerDTO) throws ConstraintsViolationException, EntityNotFoundException {
        Answer answer = makeAnswer(answerDTO, questionService.find(questionId));
        return AnswerMapper.makeAnswerDTO(answerService.create(answer));
    }
    
    /**
     * Update answer
     * @param id
     * @param answerDTO
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException 
     */
    @PutMapping("{id}")
    public void updateAnswer(@PathVariable long id, @Valid @RequestBody AnswerDTO answerDTO) throws ConstraintsViolationException, EntityNotFoundException {
        answerService.update(id, answerDTO);
    }
    
    @DeleteMapping("{id}")
    public void deleteAnswer(@PathVariable long id) throws EntityNotFoundException {
        answerService.delete(id);
    }
}
