/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionDTO {
    
    private Long id;
    
    @NotBlank(message = "The question must be provided")
    private String  question;
    
    private List<AnswerDTO> answers;
    
    public static QuestionDTOBuilder builder() {
        return new QuestionDTOBuilder();
    }
    
    public QuestionDTO(QuestionDTOBuilder builder) {
        this.id = builder.id;
        this.question = builder.question;
        this.answers = builder.answers;
    }
    
    public static class QuestionDTOBuilder {
        private Long id;
        private String  question;
        private List<AnswerDTO> answers;

        public QuestionDTOBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public QuestionDTOBuilder setQuestion(String question) {
            this.question = question;
            return this;
        }

        public QuestionDTOBuilder setAnswers(List<AnswerDTO> answers) {
            this.answers = answers;
            return this;
        }
        
        public QuestionDTO build() {
            return new QuestionDTO(this);
        }
    }
}
