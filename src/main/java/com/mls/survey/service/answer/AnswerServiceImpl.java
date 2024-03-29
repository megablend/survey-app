/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.service.answer;

import com.mls.survey.dataaccessobject.AnswerRepo;
import com.mls.survey.datatransferobject.AnswerDTO;
import com.mls.survey.domainobject.Answer;
import com.mls.survey.domainobject.Question;
import com.mls.survey.exception.ConstraintsViolationException;
import com.mls.survey.exception.EntityNotFoundException;
import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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
        answerRepo.deleteByQuestion(question.getId());
    }

    /** {@inheritDoc} */
    @Override
    public Answer find(long id) throws EntityNotFoundException {
        return findAnswer(id);
    }

    /** {@inheritDoc} */
    @Transactional
    @Override
    public List<Answer> create(List<Answer> answers) throws ConstraintsViolationException {
        List<Answer> savedAnswers;
        try {
            savedAnswers = answerRepo.saveAll(answers);
        } catch (DataIntegrityViolationException e) {
            log.warn("ConstraintsViolationException while creating answers", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return savedAnswers;
    }

    @Transactional
    @Override
    public void update(long id, AnswerDTO answerDto) throws ConstraintsViolationException, EntityNotFoundException {
        Answer answer = findAnswer(id);
        answer.setAnswer(answerDto.getAnswer());
    }

    @Transactional
    @Override
    public void delete(long id) throws EntityNotFoundException {
        Answer answer = findAnswer(id);
        answerRepo.delete(answer);
    }
    
    private Answer findAnswer(long id) throws EntityNotFoundException {
        return answerRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Unable to find the answer with the ID " + id));
    }
    
}
