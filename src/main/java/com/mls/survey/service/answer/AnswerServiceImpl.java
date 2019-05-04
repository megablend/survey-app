/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.service.answer;

import com.mls.survey.dataaccessobject.AnswerRepo;
import com.mls.survey.domainobject.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Service
@Slf4j
public class AnswerServiceImpl implements AnswerService {
    
    private final AnswerRepo answerRepo;
    
    public AnswerServiceImpl(AnswerRepo answerRepo) {
        this.answerRepo = answerRepo;
    }

    /** {@inheritDoc} */
    @Override
    public void deleteAnswersByQuestion(Question question) {
        answerRepo.deleteAnswers(question);
    }
    
}
