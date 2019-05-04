/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.service.question;

import com.mls.survey.dataaccessobject.QuestionRepo;
import com.mls.survey.datatransferobject.QuestionDTO;
import com.mls.survey.domainobject.Question;
import com.mls.survey.exception.ConstraintsViolationException;
import com.mls.survey.exception.EntityNotFoundException;
import com.mls.survey.service.answer.AnswerService;
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
public class QuestionServiceImpl implements QuestionService {
    
    private final QuestionRepo questionRepo;
    private final AnswerService answerService;
    
    public QuestionServiceImpl(QuestionRepo questionRepo, AnswerService answerService) {
        this.questionRepo = questionRepo;
        this.answerService = answerService;
    }

    /** {@inheritDoc} */
    @Override
    public Question find(long id) throws EntityNotFoundException {
        return questionRepo.findByIdAndDeleted(id, false).orElseThrow(() -> new EntityNotFoundException("Unable to find the question with the ID " + id));
    }

    /** {@inheritDoc} */
    @Transactional
    @Override
    public Question create(Question question) throws ConstraintsViolationException {
        Question savedQuestion;
        try {
            savedQuestion = questionRepo.save(question);
        } catch (DataIntegrityViolationException e) {
            log.warn("ConstraintsViolationException while creating question: {}", question, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return savedQuestion;
    }

    /** {@inheritDoc} */
    @Transactional
    @Override
    public void update(long id, QuestionDTO questionDto) throws ConstraintsViolationException, EntityNotFoundException {
        Question question = findQuestion(id);
        question.setQuestion(questionDto.getQuestion());
    }

    /** {@inheritDoc} */
    @Transactional
    @Override
    public void delete(long id) throws EntityNotFoundException {
        Question question = findQuestion(id);
        answerService.deleteAnswersByQuestion(question); // delete all answers associated with this question if any using soft-delete
        question.setDeleted(true);
    }
    
    private Question findQuestion(long id) throws EntityNotFoundException {
        return questionRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Unable to find the question with the ID " + id));
    }

    /** {@inheritDoc} */
    @Override
    public List<Question> getQuestions() {
        return questionRepo.findByDeleted(false);
    }
    
}
