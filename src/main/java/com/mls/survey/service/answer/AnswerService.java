/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.service.answer;

import com.mls.survey.domainobject.Question;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public interface AnswerService {
    
    /**
     * Deletes answers by question
     * @param question 
     */
    void deleteAnswersByQuestion(Question question);
}
