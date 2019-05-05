/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.controller;

import com.mls.survey.datatransferobject.ParticipantResponseDTO;
import com.mls.survey.exception.ConstraintsViolationException;
import com.mls.survey.exception.EntityNotFoundException;
import com.mls.survey.service.response.SurveyResponseService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@RestController
@RequestMapping("v1/responses")
public class ResponseController {
    
    @Autowired
    private SurveyResponseService surveyResponseService;
    
    /**
     * Create Participants Response
     * @param response
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException 
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createResponse(@Valid @RequestBody ParticipantResponseDTO response) throws ConstraintsViolationException, EntityNotFoundException {
        surveyResponseService.create(response);
    }
}
