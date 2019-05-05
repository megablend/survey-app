/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.service;

import com.mls.survey.dataaccessobject.QuestionRepo;
import com.mls.survey.domainobject.Question;
import com.mls.survey.exception.EntityNotFoundException;
import com.mls.survey.service.answer.AnswerService;
import com.mls.survey.service.question.QuestionService;
import com.mls.survey.service.question.QuestionServiceImpl;
import static com.mls.survey.util.Util.mockQuestion;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public class QuestionServiceTest {
    
    private QuestionService questionService;
    
    @MockBean
    private QuestionRepo questionRepo;
    
    @MockBean
    private AnswerService answerService;
    
    @Before
    public void setup() {
        questionService = new QuestionServiceImpl(questionRepo, answerService);
    }
    
    /**
     * Test case for find question
     * @throws EntityNotFoundException 
     */
    @Test
    public void testFindQuestion() throws EntityNotFoundException {
        
        Question mockQuestion = mockQuestion();
        when(questionRepo.findById(isA(Long.class))).thenReturn(Optional.of(mockQuestion));
        
        Question question = questionService.find(1);
        assertNotNull(question);
        assertEquals(question.getQuestion(), mockQuestion.getQuestion());
    }
}
