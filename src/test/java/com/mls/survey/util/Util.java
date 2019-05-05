/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mls.survey.datatransferobject.AnswerDistributionDTO;
import com.mls.survey.datatransferobject.ResponseDistributionDTO;
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
     * Mock Response Distribution
     * @return 
     */
    public static ResponseDistributionDTO mockResponseDistribution() {
        return new ResponseDistributionDTO(2, mockResponseAnswers());
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
}
