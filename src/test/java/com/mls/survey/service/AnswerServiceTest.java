/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.service;

import com.mls.survey.dataaccessobject.AnswerRepo;
import com.mls.survey.datatransferobject.AnswerDTO;
import com.mls.survey.domainobject.Answer;
import com.mls.survey.domainobject.Question;
import com.mls.survey.exception.ConstraintsViolationException;
import com.mls.survey.exception.EntityNotFoundException;
import com.mls.survey.service.answer.AnswerService;
import com.mls.survey.service.answer.AnswerServiceImpl;
import static com.mls.survey.util.Util.mockAnswer;
import static com.mls.survey.util.Util.mockAnswers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public class AnswerServiceTest {
    
    private AnswerRepo answerRepo;
    private AnswerService answerService;
    
    @Before
    public void setup() {
        this.answerRepo = mock(AnswerRepo.class);
        this.answerService = new AnswerServiceImpl(answerRepo);
    }
    
    /**
     * Test case for delete answers by question
     */
    @Test
    public void testDeleteAnswersByQuestion() {
        
        // mock dependencies
        doNothing().when(answerRepo).deleteByQuestion(isA(Long.class));
        
        answerService.deleteAnswersByQuestion(new Question());
        verify(answerRepo, times(1)).deleteByQuestion(isA(Long.class));
    }
    
    /**
     * Test case for find answer
     * @throws EntityNotFoundException 
     */
    @Test
    public void testFindQuestion() throws EntityNotFoundException {
        
        Answer mockAnswer = mockAnswer();
        when(answerRepo.findById(isA(Long.class))).thenReturn(Optional.of(mockAnswer));
        
        Answer answer = answerService.find(1);
        assertNotNull(answer);
        assertEquals(answer.getAnswer(), mockAnswer.getAnswer());
    }
    
    /**
     * Test case for create answer
     * @throws ConstraintsViolationException 
     */
    @Test
    public void testCreateAnswer() throws ConstraintsViolationException {
        
        List<Answer> mockAnswers = mockAnswers();
        when(answerRepo.saveAll(any())).thenReturn(mockAnswers);
        
        List<Answer> answers = answerService.create(new ArrayList<>());
        
        assertNotNull(answers);
        assertThat(answers.size(), is(mockAnswers.size()));
        verify(answerRepo, times(1)).saveAll(any());
    }
    
    /**
     * Test case for update answer
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException 
     */
    @Test
    public void testUpdateAnswer() throws ConstraintsViolationException, EntityNotFoundException {
        
        Answer mockAnswer = mockAnswer();
        when(answerRepo.findById(isA(Long.class))).thenReturn(Optional.of(mockAnswer));
        
        String updateAnswer = "This is an updated answer";
        answerService.update(1, new AnswerDTO(1L, updateAnswer));
        assertEquals(mockAnswer.getAnswer(), updateAnswer);
        verify(answerRepo, times(1)).findById(isA(Long.class));
    }
    
    /**
     * Test case for delete answer
     * @throws EntityNotFoundException 
     */
    @Test
    public void testDeleteAnswer() throws EntityNotFoundException {
        
        Answer mockAnswer = mockAnswer();
        when(answerRepo.findById(isA(Long.class))).thenReturn(Optional.of(mockAnswer));
        doNothing().when(answerRepo).delete(isA(Answer.class));
        
        answerService.delete(1L);
        verify(answerRepo, times(1)).findById(isA(Long.class));
        verify(answerRepo, times(1)).delete(isA(Answer.class));
    }
}
