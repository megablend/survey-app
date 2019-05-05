/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.controller;

import com.mls.survey.datatransferobject.QuestionDTO;
import com.mls.survey.domainobject.Question;
import static com.mls.survey.mapper.QuestionMapper.makeQuestionDTO;
import static com.mls.survey.mapper.QuestionMapper.makeQuestionDTOList;
import com.mls.survey.service.question.QuestionService;
import com.mls.survey.service.response.SurveyResponseService;
import static com.mls.survey.util.Util.convertObjectToJson;
import static com.mls.survey.util.Util.mockQuestion;
import static com.mls.survey.util.Util.mockQuestions;
import org.hamcrest.Matchers;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@RunWith(SpringRunner.class)
@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private WebApplicationContext wac;
    
    @MockBean
    private QuestionService questionService;
    
    @MockBean
    private SurveyResponseService surveyResponseService;
    
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    /**
     * Test Create Question
     * @throws Exception 
     */
    @Test
    public void testCreateQuestion() throws Exception {
        String request;
        MvcResult result;
        
        // With Invalid Input
        request = "{ \"question\": \"\" }";
        
        mvc.perform(post("/v1/questions")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request)
                            .accept(MediaType.APPLICATION_JSON))
                            .andDo(print())
                            .andExpect(status().isBadRequest())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                            .andExpect(jsonPath("$.error[0]", Matchers.is("The question must be provided")));
        
        // With valid input
        request = "{ \"question\": \"New Question Created?\" }";
        
        // mock dependencies
        Question question = mockQuestion();
        when(questionService.create(isA(Question.class))).thenReturn(question);
        
        result = mvc.perform(post("/v1/questions")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request)
                            .accept(MediaType.APPLICATION_JSON))
                            .andDo(print())
                            .andExpect(status().isCreated())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                            .andExpect(jsonPath("$.question", Matchers.is(question.getQuestion()))).andReturn();
        
        verify(questionService, times(1)).create(any());
        assertEquals(result.getResponse().getContentAsString(), 
                            convertObjectToJson(makeQuestionDTO(question)));
        
        // with invalid 
    }
    
    /**
     * Test case for update question
     * @throws Exception 
     */
    @Test
    public void testUpdateQuestion() throws Exception {
        
        String request = "{ \"question\": \"New Question Created?\" }";
        
        // mock dependencies
        doNothing().when(questionService).update(isA(Long.class), isA(QuestionDTO.class));
        
        mvc.perform(put("/v1/questions/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
        
        verify(questionService, times(1)).update(isA(Long.class), isA(QuestionDTO.class));
    }
    
    /**
     * Test case for delete question
     * @throws Exception 
     */
    @Test
    public void testDeleteQuestion() throws Exception {
        
        // mock dependencies
        doNothing().when(questionService).delete(isA(Long.class));
        
        mvc.perform(delete("/v1/questions/1")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        
        verify(questionService, times(1)).delete(isA(Long.class));
    }
    
    /**
     * Test case for get questions
     * @throws Exception 
     */
    @Test
    public void testGetQuestions() throws Exception {
        
        // mock dependencies
        when(questionService.getQuestions()).thenReturn(mockQuestions());
        
        MvcResult result = mvc.perform(get("/v1/questions")
                            .accept(MediaType.APPLICATION_JSON))
                            .andDo(print())
                            .andExpect(status().isOk())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                            .andExpect(jsonPath("$.[0].question", Matchers.is(mockQuestions().get(0).getQuestion())))
                            .andExpect(jsonPath("$.[1].question", Matchers.is(mockQuestions().get(1).getQuestion()))).andReturn();
        
        verify(questionService, times(1)).getQuestions();
        assertEquals(result.getResponse().getContentAsString(), 
                convertObjectToJson(makeQuestionDTOList(mockQuestions())));
    }
}
