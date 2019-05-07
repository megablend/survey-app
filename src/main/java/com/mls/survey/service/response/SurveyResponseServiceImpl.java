/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.service.response;

import com.mls.survey.dataaccessobject.SurveyResponseRepo;
import com.mls.survey.datatransferobject.AnswerDistributionDTO;
import com.mls.survey.datatransferobject.ParticipantResponseDTO;
import com.mls.survey.datatransferobject.ResponseDTO;
import com.mls.survey.datatransferobject.ResponseDistributionDTO;
import com.mls.survey.dataaccessobject.projection.AnswerOnly;
import com.mls.survey.dataaccessobject.projection.AnswerOnly.QuestionAnswer;
import com.mls.survey.domainobject.Answer;
import com.mls.survey.domainobject.Participant;
import com.mls.survey.domainobject.Question;
import com.mls.survey.domainobject.SurveyResponse;
import com.mls.survey.exception.ConstraintsViolationException;
import com.mls.survey.exception.EntityNotFoundException;
import com.mls.survey.service.answer.AnswerService;
import com.mls.survey.service.participant.ParticipantService;
import com.mls.survey.service.question.QuestionService;
import static com.mls.survey.util.Util.answersPercentage;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Service
@Slf4j
public class SurveyResponseServiceImpl implements SurveyResponseService {
    
    private final SurveyResponseRepo surveyResponseRepo;
    private final QuestionService questionService;
    private final ParticipantService participantService;
    private final AnswerService answerService;
    private final DecimalFormat decimalFormat = new DecimalFormat(".##");
    
    public SurveyResponseServiceImpl(SurveyResponseRepo surveyResponseRepo, 
                                        QuestionService questionService, 
                                        ParticipantService participantService, AnswerService answerService) {
        this.surveyResponseRepo = surveyResponseRepo;
        this.questionService = questionService;
        this.participantService = participantService;
        this.answerService = answerService;
    }

    /** {@inheritDoc} */
    @Transactional
    @Override
    public void create(ParticipantResponseDTO participantResponseDTO) throws ConstraintsViolationException, EntityNotFoundException {
        Participant participant = participantService.findByEmail(participantResponseDTO.getEmail(), 
                                                                    participantResponseDTO.getPhoneNumber());
        
        for (ResponseDTO r: participantResponseDTO.getResponses()) { // Using this iteration pattern because you cannot throw a checked exception inside a lambda expression
            Question question = questionService.find(r.getQuestionId());
            Answer answer = answerService.find(r.getAnswerId());
            
            if (null != surveyResponseRepo.findByQuestionAndParticipantAndAnswer(question, participant, answer))
                continue;
            
            SurveyResponse surveyResponse = new SurveyResponse(question, answer, participant);
            surveyResponseRepo.save(surveyResponse);
        }
    }

    /** {@inheritDoc} */
    @Override
    public ResponseDistributionDTO getResponsesToQuestion(Question question) {
        List<AnswerOnly> responses = surveyResponseRepo.findByQuestion(question);
        Map<QuestionAnswer, List<AnswerOnly>> answersGrouped = responses.stream().collect(groupingBy(AnswerOnly::getAnswer));
        return computeAnswersDistribution(responses.size(), question.getQuestion(), answersGrouped);
    }
    
    private ResponseDistributionDTO computeAnswersDistribution(int total, String question, Map<QuestionAnswer, List<AnswerOnly>> answersGrouped) {
        List<AnswerDistributionDTO> answersStat = answersGrouped.entrySet().stream()
                                                                           .map(e -> getAnswersDistribution(e, total))
                                                                           .collect(toList());
        return new ResponseDistributionDTO(question, total, answersStat);
    }
    
    private AnswerDistributionDTO getAnswersDistribution(Entry entry, int total) {
        QuestionAnswer answer = (QuestionAnswer) entry.getKey();
        List<AnswerOnly> questionAnswers = (List<AnswerOnly>) entry.getValue();
        return new AnswerDistributionDTO(answer.getAnswer(), decimalFormat.format(answersPercentage(questionAnswers.size(), total)) + "%");
    }
}
