/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.service.answer;

import com.mls.survey.datatransferobject.AnswerDTO;
import com.mls.survey.domainobject.Answer;
import com.mls.survey.domainobject.Question;
import com.mls.survey.exception.ConstraintsViolationException;
import com.mls.survey.exception.EntityNotFoundException;
import java.util.List;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public interface AnswerService {
    
    /**
     * Find an answer by ID
     * @param id
     * @return
     * @throws EntityNotFoundException 
     */
    Answer find(long id) throws EntityNotFoundException;
    
    /**
     * Creates a new answer
     * @param answers
     * @return
     * @throws ConstraintsViolationException 
     */
    List<Answer> create(List<Answer> answers) throws ConstraintsViolationException;
    
    /**
     * Update answer
     * @param id
     * @param answerDto
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException 
     */
    void update(long id, AnswerDTO answerDto) throws ConstraintsViolationException, EntityNotFoundException;
    
    /**
     * Deletes an answer
     * @param id
     * @throws EntityNotFoundException 
     */
    void delete(long id) throws EntityNotFoundException;
    
    /**
     * Deletes answers by question
     * @param question 
     */
    void deleteAnswersByQuestion(Question question);
}
