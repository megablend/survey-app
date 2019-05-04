/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.service.question;

import com.mls.survey.datatransferobject.QuestionDTO;
import com.mls.survey.domainobject.Question;
import com.mls.survey.exception.ConstraintsViolationException;
import com.mls.survey.exception.EntityNotFoundException;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public interface QuestionService {
    
    /**
     * Find a question by ID
     * @param id
     * @return
     * @throws EntityNotFoundException 
     */
    Question find(long id) throws EntityNotFoundException;
    
    /**
     * Creates a new question
     * @param question
     * @return
     * @throws ConstraintsViolationException 
     */
    Question create(Question question) throws ConstraintsViolationException;
    
    /**
     * Update question
     * @param id
     * @param questionDto
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException 
     */
    void update(long id, QuestionDTO questionDto) throws ConstraintsViolationException, EntityNotFoundException;
    
    /**
     * Deletes a question
     * @param id
     * @throws EntityNotFoundException 
     */
    void delete(long id) throws EntityNotFoundException;
}
