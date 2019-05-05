/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.service.participant;

import com.mls.survey.domainobject.Participant;
import com.mls.survey.exception.ConstraintsViolationException;
import com.mls.survey.exception.EntityNotFoundException;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public interface ParticipantService {
    
    /**
     * Get Participant
     * @param id
     * @return
     * @throws EntityNotFoundException 
     */
    Participant find(long id) throws EntityNotFoundException;
    
    /**
     * Find participant by email
     * @param email
     * @param phoneNumber
     * @return
     * @throws ConstraintsViolationException 
     */
    Participant findByEmail(String email, String phoneNumber) throws ConstraintsViolationException;
    
    /**
     * Create a new participant
     * @param participant
     * @return
     * @throws ConstraintsViolationException 
     */
    Participant create(Participant participant) throws ConstraintsViolationException;
}
