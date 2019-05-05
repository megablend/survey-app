/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.service.response;

import com.mls.survey.datatransferobject.ParticipantResponseDTO;
import com.mls.survey.datatransferobject.ResponseDistributionDTO;
import com.mls.survey.domainobject.Question;
import com.mls.survey.exception.ConstraintsViolationException;
import com.mls.survey.exception.EntityNotFoundException;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public interface SurveyResponseService {
    
    /**
     * Creates a new participant response
     * @param participantResponseDTO
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException 
     */
    void create(ParticipantResponseDTO participantResponseDTO) throws ConstraintsViolationException, EntityNotFoundException;
    
    /**
     * Get answers distribution to a question
     * @param question
     * @return 
     */
    ResponseDistributionDTO getResponsesToQuestion(Question question);
}
