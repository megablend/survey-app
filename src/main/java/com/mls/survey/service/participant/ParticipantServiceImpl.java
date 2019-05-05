/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.service.participant;

import com.mls.survey.dataaccessobject.ParticipantRepo;
import com.mls.survey.domainobject.Participant;
import com.mls.survey.exception.ConstraintsViolationException;
import com.mls.survey.exception.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Service
@Slf4j
public class ParticipantServiceImpl implements ParticipantService {
    
    private final ParticipantRepo participantRepo;
    
    public ParticipantServiceImpl(ParticipantRepo participantRepo) {
        this.participantRepo = participantRepo;
    }

    /** {@inheritDoc} */
    @Override
    public Participant find(long id) throws EntityNotFoundException {
        return findParticipant(id);
    }

    /** {@inheritDoc} */
    @Transactional
    @Override
    public Participant create(Participant participant) throws ConstraintsViolationException {
        Participant savedParticipant;
        try {
            savedParticipant = participantRepo.save(participant);
        } catch (DataIntegrityViolationException e) {
            log.warn("ConstraintsViolationException while creating participant: {}", participant, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return savedParticipant;
    }
    
    private Participant findParticipant(long id) throws EntityNotFoundException {
        return participantRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Unable to find the participant with the ID " + id));
    }

    @Override
    public Participant findByEmail(String email, String phoneNumber) throws ConstraintsViolationException {
        Participant participant = participantRepo.findByEmail(email).orElseGet(() -> null);
        
        if (null == participant)
            participant = create(new Participant(email, phoneNumber));
        
        return participant;
    }
    
    
}
