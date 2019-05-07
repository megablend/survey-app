/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.service;

import com.mls.survey.dataaccessobject.SurveyResponseRepo;
import com.mls.survey.dataaccessobject.projection.AnswerOnly;
import com.mls.survey.datatransferobject.ParticipantResponseDTO;
import com.mls.survey.datatransferobject.ResponseDistributionDTO;
import com.mls.survey.domainobject.Answer;
import com.mls.survey.domainobject.Participant;
import com.mls.survey.domainobject.Question;
import com.mls.survey.exception.ConstraintsViolationException;
import com.mls.survey.exception.EntityNotFoundException;
import com.mls.survey.service.answer.AnswerService;
import com.mls.survey.service.participant.ParticipantService;
import com.mls.survey.service.question.QuestionService;
import com.mls.survey.service.response.SurveyResponseService;
import com.mls.survey.service.response.SurveyResponseServiceImpl;
import static com.mls.survey.util.Util.mockAnswer;
import static com.mls.survey.util.Util.mockQuestion;
import static com.mls.survey.util.Util.mockResponseDTO;
import static com.mls.survey.util.Util.mockResponseDistAnswers;
import java.util.List;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public class SurveyResponseServiceTest {
    
    private SurveyResponseRepo surveyResponseRepo;
    private QuestionService questionService;
    private ParticipantService participantService;
    private AnswerService answerService;
    private SurveyResponseService surveyResponseService;
    
    @Before
    public void setup() {
        this.surveyResponseRepo = mock(SurveyResponseRepo.class);
        this.questionService = mock(QuestionService.class);
        this.participantService = mock(ParticipantService.class);
        this.answerService = mock(AnswerService.class);
        this.surveyResponseService = new SurveyResponseServiceImpl(surveyResponseRepo, questionService, participantService, answerService);
    }
    
    /**
     * Test case for create response
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException 
     */
    @Test
    public void testCreateResponse() throws ConstraintsViolationException, EntityNotFoundException {
        
        Question mockQuestion = mockQuestion();
        Answer mockAnswer = mockAnswer();
        when(participantService.findByEmail(isA(String.class), isA(String.class))).thenReturn(new Participant());
        when(questionService.find(isA(Long.class))).thenReturn(mockQuestion);
        when(answerService.find(isA(Long.class))).thenReturn(mockAnswer);
        when(surveyResponseRepo.findByQuestionAndParticipantAndAnswer(isA(Question.class), 
                isA(Participant.class), isA(Answer.class))).thenReturn(null);
        
        surveyResponseService.create(mockParticipantResponseDTO());
        verify(participantService, times(1)).findByEmail(isA(String.class), isA(String.class));
        verify(questionService, times(3)).find(isA(Long.class));
        verify(answerService, times(3)).find(isA(Long.class));
        verify(surveyResponseRepo,times(3)).findByQuestionAndParticipantAndAnswer(isA(Question.class), 
                isA(Participant.class), isA(Answer.class));
    }
    
    /**
     * Test case for get responses to a question
     * 
     */
    @Test
    public void testGetResponsesToQuestion() {
        List<AnswerOnly> answers = mockResponseDistAnswers();
        when(surveyResponseRepo.findByQuestion(isA(Question.class))).thenReturn(answers);
        
        Question question = new Question("Question Asked");
        ResponseDistributionDTO response = surveyResponseService.getResponsesToQuestion(question);
        
        assertNotNull(response);
        assertEquals(answers.size(), response.getAnswers().size());
        assertThat(response.getQuestion(), is(question.getQuestion()));
        assertThat(response.getTotal(), is(answers.size()));
        assertThat(response.getAnswers(), hasItems(
                    hasProperty("answer", is(answers.get(0).getAnswer().getAnswer())),
                    hasProperty("percentage", is("50.0%"))));
        verify(surveyResponseRepo, times(1)).findByQuestion(isA(Question.class));
    }
    
    public ParticipantResponseDTO mockParticipantResponseDTO() {
        return new ParticipantResponseDTO("mls@gmail.com", "0806108165", mockResponseDTO());
    }
}
