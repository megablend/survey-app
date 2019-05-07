/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.controller;

import com.mls.survey.datatransferobject.ParticipantResponseDTO;
import com.mls.survey.service.response.SurveyResponseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ResponseController.class)
public class ResponseControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private WebApplicationContext wac;
    
    @MockBean
    private SurveyResponseService surveyResponseService;
    
    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    /**
     * Test create response
     * @throws Exception 
     */
    @Test
    public void testCreateResponse() throws Exception {
        
        String request = "{ \"email\": \"test@gmail.com\", \"phoneNumber\": \"1111111111\", \"responses\": [ { \"questionId\": 1, \"answerId\": 2 } ] }";
        
        // mock dependencies
        doNothing().when(surveyResponseService).create(isA(ParticipantResponseDTO.class));
        
        mvc.perform(post("/v1/responses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        
        verify(surveyResponseService, times(1)).create(isA(ParticipantResponseDTO.class));
    }
}
