/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mls.survey.dataaccessobject.projection.AnswerOnly;
import com.mls.survey.datatransferobject.AnswerDistributionDTO;
import com.mls.survey.datatransferobject.ResponseDTO;
import com.mls.survey.datatransferobject.ResponseDistributionDTO;
import com.mls.survey.domainobject.Answer;
import com.mls.survey.domainobject.Question;
import java.util.List;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Slf4j
public final class Util {
    
    private Util() {}
    
    /**
     * Mock Question
     * @return 
     */
    public static Question mockQuestion() {
        return new Question("New Question Created?");
    }
    
    /**
     * Mock Questions
     * @return 
     */
    public static List<Question> mockQuestions() {
        return Stream.of(new Question("Do you love coding?"), 
                         new Question("How many hours do you spend coding?")).collect(toList());
    }
    
    /**
     * Mock answers
     * @return 
     */
    public static List<Answer> mockAnswers() {
        Question question = mockQuestion();
        return Stream.of(new Answer("yes", question),
                        new Answer("no", question)).collect(toList());
    }
    
    /**
     * Mock Response DTO
     * @return 
     */
    public static List<ResponseDTO> mockResponseDTO() {
        return Stream.of(new ResponseDTO(1L, 1L), new ResponseDTO(2L, 2L), new ResponseDTO(3L, 3L)).collect(toList());
    }
    
    public static List<AnswerOnly> mockResponseDistAnswers() {
        return Stream.of((AnswerOnly) () -> () -> "yes", (AnswerOnly) () -> () -> "no").collect(toList());
    }
    
    /**
     * Mock Response Distribution
     * @return 
     */
    public static ResponseDistributionDTO mockResponseDistribution() {
        return new ResponseDistributionDTO("Question Asked", 2, mockResponseAnswers());
    }
    
    /**
     * Mock Answer
     * @return 
     */
    public static Answer mockAnswer() {
        return new Answer("yes", mockQuestion());
    }
    
    /**
     * Mock answer distribution
     * @return 
     */
    public static List<AnswerDistributionDTO> mockResponseAnswers() {
        return Stream.of(new AnswerDistributionDTO("yes", "50.0%"),
                         new AnswerDistributionDTO("no", "50.0%")).collect(toList());
    }
    
    /**
     * Convert and object to a string
     * @param <T>
     * @param object
     * @return 
     */
    public static <T> String convertObjectToJson(T object) {
        try {
            ObjectMapper objMapper = new ObjectMapper();
            return objMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Unable to convert object to a string", e);
        }
        return null;
    }
    
    public static double answersPercentage(int totalAnswers, int totalResponse) {
        return ((double) (totalAnswers * 100) / totalResponse);
    }
}
