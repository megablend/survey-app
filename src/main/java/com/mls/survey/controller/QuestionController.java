/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.controller;

import com.mls.survey.datatransferobject.QuestionDTO;
import com.mls.survey.domainobject.Question;
import com.mls.survey.exception.ConstraintsViolationException;
import static com.mls.survey.mapper.QuestionMapper.makeQuestion;
import static com.mls.survey.mapper.QuestionMapper.makeQuestionDTO;
import com.mls.survey.service.question.QuestionService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
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
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionDTO createQuestion(@Valid @RequestBody QuestionDTO questionDto) throws ConstraintsViolationException {
        Question question = makeQuestion(questionDto);
        return makeQuestionDTO(questionService.create(question));
    }
}
