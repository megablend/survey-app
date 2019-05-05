/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.service;

import com.mls.survey.dataaccessobject.QuestionRepo;
import com.mls.survey.datatransferobject.QuestionDTO;
import com.mls.survey.domainobject.Question;
import com.mls.survey.exception.ConstraintsViolationException;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import com.mls.survey.exception.EntityNotFoundException;
import com.mls.survey.service.answer.AnswerService;
import com.mls.survey.service.question.QuestionService;
import com.mls.survey.service.question.QuestionServiceImpl;
import static com.mls.survey.util.Util.mockQuestion;
import static com.mls.survey.util.Util.mockQuestions;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public class QuestionServiceTest {
    
    private QuestionService questionService;
    
    private QuestionRepo questionRepo;
    
    private AnswerService answerService;
    
    @Before
    public void setup() {
        questionRepo = mock(QuestionRepo.class);
        answerService = mock(AnswerService.class);
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
    
    /**
     * Test case for create question
     * @throws ConstraintsViolationException 
     */
    @Test
    public void testCreateQuestion() throws ConstraintsViolationException {
        
        Question mockQuestion = mockQuestion();
        when(questionRepo.save(isA(Question.class))).thenReturn(mockQuestion);
        
        Question question = questionService.create(new Question());
        
        assertNotNull(question);
        assertEquals(question.getQuestion(), mockQuestion.getQuestion());
        verify(questionRepo, times(1)).save(isA(Question.class));
    }
    
    /**
     * Test case for create question with invalid data
     * @throws ConstraintsViolationException 
     */
    @Test(expected = ConstraintsViolationException.class)
    public void testCreateQuestionWithInvalidData() throws ConstraintsViolationException {
        
        when(questionRepo.save(isA(Question.class))).thenThrow(DataIntegrityViolationException.class);
        
        questionService.create(new Question());
        fail("ConstraintsViolationException thrown");
    }
    
    /**
     * Test case for update question
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException 
     */
    @Test
    public void testUpdateQuestion() throws ConstraintsViolationException, EntityNotFoundException {
        
        Question mockQuestion = mockQuestion();
        when(questionRepo.findById(isA(Long.class))).thenReturn(Optional.of(mockQuestion));
        
        questionService.update(1, new QuestionDTO());
    }
    
    /**
     * Test case for delete question
     * @throws EntityNotFoundException 
     */
    @Test
    public void testDeleteQuestion() throws EntityNotFoundException {
        
        Question mockQuestion = mockQuestion();
        when(questionRepo.findById(isA(Long.class))).thenReturn(Optional.of(mockQuestion));
        doNothing().when(answerService).deleteAnswersByQuestion(isA(Question.class));
        doNothing().when(questionRepo).delete(isA(Question.class));
        
        questionService.delete(1);
    }
    
    /**
     * Test case for get questions
     */
    @Test
    public void testGetQuestions() {
        List<Question> mockQuestions = mockQuestions();
        when(questionRepo.findAll()).thenReturn(mockQuestions);
        
        List<Question> questions = questionService.getQuestions();
        assertNotNull(questions);
        assertThat(questions.size(), is(mockQuestions.size()));
        assertThat(questions, hasItems(
                    hasProperty("question", is(mockQuestions.get(0).getQuestion())),
                    hasProperty("question", is(mockQuestions.get(1).getQuestion()))));
    }
}
