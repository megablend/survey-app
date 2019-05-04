/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.mapper;

import com.mls.survey.datatransferobject.AnswerDTO;
import com.mls.survey.domainobject.Answer;
import java.util.Collection;
import java.util.List;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public class AnswerMapper {
    
    private AnswerMapper() {} 
    
    /**
     * Map answer DTO
     * @param answer
     * @return 
     */
    public static AnswerDTO makeAnswerDTO(Answer answer) {
        return new AnswerDTO(answer.getId(), answer.getAnswer());
    }
    
    /**
     * Map a list of answer DTO
     * @param answers
     * @return 
     */
    public static List<AnswerDTO> makeAnswerDTOList(Collection<Answer> answers) {
        return answers.stream().map(AnswerMapper::makeAnswerDTO).collect(toList());
    }
}
