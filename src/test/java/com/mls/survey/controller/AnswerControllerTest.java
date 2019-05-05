/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.controller;

import com.mls.survey.datatransferobject.AnswerDTO;
import com.mls.survey.domainobject.Answer;
import com.mls.survey.exception.ConstraintsViolationException;
import static com.mls.survey.mapper.AnswerMapper.makeAnswerDTOList;
import com.mls.survey.service.answer.AnswerService;
import com.mls.survey.service.question.QuestionService;
import static com.mls.survey.util.Util.convertObjectToJson;
import static com.mls.survey.util.Util.mockAnswers;
import static com.mls.survey.util.Util.mockQuestion;
import java.util.List;
import org.hamcrest.Matchers;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AnswerController.class)
public class AnswerControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private WebApplicationContext wac;
    
    @MockBean
    private QuestionService questionService;
    
    @MockBean
    private AnswerService answerService;
    
    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    /**
     * Test case for create answer
     * @throws Exception 
     */
    @Test
    public void testCreateAnswer() throws Exception {
        
        String request;
        MvcResult result;
        
        // With Invalid Input
        request = "[ { \"answer\": \"yes\" }, { \"answer\": \"no\" }, { \"answer\": \"\" } ]";
        
        when(questionService.find(isA(Long.class))).thenReturn(mockQuestion());
        when(answerService.create(any())).thenThrow(new ConstraintsViolationException("The answer to this question must be provided"));
        mvc.perform(post("/v1/answers/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isBadRequest())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                            .andExpect(jsonPath("$.error", Matchers.is("The answer to this question must be provided")));
        
        reset(questionService);
        reset(answerService);
        
        // With valid input
        request = "[ { \"answer\": \"yes\" }, { \"answer\": \"no\" } ]";
        
        // mock dependencies
        List<Answer> answers = mockAnswers();
        when(questionService.find(isA(Long.class))).thenReturn(mockQuestion());
        when(answerService.create(any())).thenReturn(answers);
        
        result = mvc.perform(post("/v1/answers/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isCreated())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                            .andExpect(jsonPath("$.[0].answer", Matchers.is(answers.get(0).getAnswer()))).andReturn();
        
        verify(questionService, times(1)).find(isA(Long.class));
        verify(answerService, times(1)).create(any());
        assertEquals(result.getResponse().getContentAsString(), 
                            convertObjectToJson(makeAnswerDTOList(answers)));
    }
    
    /**
     * Test case for update answer
     * @throws Exception 
     */
    @Test
    public void testUpdateAnswer() throws Exception {
        
        String request = "{ \"answer\": \"yes yes\" }";
        
        // mock dependencies
        doNothing().when(answerService).update(isA(Long.class), isA(AnswerDTO.class));
        
        mvc.perform(put("/v1/answers/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        
        verify(answerService, times(1)).update(isA(Long.class), isA(AnswerDTO.class));
    }
    
    /**
     * Test case for delete answer
     * @throws Exception 
     */
    @Test
    public void testDeleteAnswer() throws Exception {
        
        // mock dependencies
        doNothing().when(answerService).delete(isA(Long.class));
        
        mvc.perform(delete("/v1/answers/1")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        
        verify(answerService, times(1)).delete(isA(Long.class));
    }
}
