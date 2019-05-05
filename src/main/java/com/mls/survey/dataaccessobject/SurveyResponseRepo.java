/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.dataaccessobject;

import com.mls.survey.dataaccessobject.projection.AnswerOnly;
import com.mls.survey.domainobject.Answer;
import com.mls.survey.domainobject.Participant;
import com.mls.survey.domainobject.Question;
import com.mls.survey.domainobject.SurveyResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public interface SurveyResponseRepo extends JpaRepository<SurveyResponse, Long> {
    
    SurveyResponse findByQuestionAndParticipantAndAnswer(Question question, Participant participant, Answer answer);
    
    List<AnswerOnly> findByQuestion(Question question);
}
