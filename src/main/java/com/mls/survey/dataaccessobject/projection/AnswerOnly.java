/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.dataaccessobject.projection;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public interface AnswerOnly {
    
    QuestionAnswer getAnswer();
    
    interface QuestionAnswer {
        String getAnswer();
    }
}
