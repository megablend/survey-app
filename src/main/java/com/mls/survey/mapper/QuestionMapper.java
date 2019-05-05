/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.mapper;

import com.mls.survey.datatransferobject.QuestionDTO;
import com.mls.survey.datatransferobject.QuestionDTO.QuestionDTOBuilder;
import com.mls.survey.domainobject.Question;
import static com.mls.survey.mapper.AnswerMapper.makeAnswerDTOList;
import java.util.Collection;
import java.util.List;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public final class QuestionMapper {
    
    private QuestionMapper() {} // prevent this class from being initialized
    
    /**
     * Maps the QuestionDTO to the Question object
     * @param question
     * @return 
     */
    public static Question makeQuestion(QuestionDTO question) {
        return new Question(question.getQuestion());
    }
    
    /**
     * Maps the Question Object to the QuestionDTO Object
     * @param question
     * @return 
     */
    public static QuestionDTO makeQuestionDTO(Question question) {
        QuestionDTOBuilder builder = QuestionDTO.builder().setId(question.getId())
                                                          .setQuestion(question.getQuestion());
        
        if (null != question.getAnswers() && !question.getAnswers().isEmpty()) 
            builder.setAnswers(makeAnswerDTOList(question.getAnswers()));
            
        return builder.build();
    }
    
    /**
     * Converts a list of  Question objects to QuestionDTO
     * @param question
     * @return 
     */
    public static List<QuestionDTO> makeQuestionDTOList(Collection<Question> question) {
        return question.stream().map(QuestionMapper::makeQuestionDTO).collect(toList());
    }
}
