/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.domainobject;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Entity
@Table(name = "survey_reponses", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"answer", "participant"}, name = "uc_asnwer_participant")
})
@Data
@NoArgsConstructor
public class SurveyResponse extends DateCreated {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @NotNull(message = "The question must not be null")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question", referencedColumnName ="id")
    private Question question;
    
    @NotNull(message = "The answer must not be null")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "answer", referencedColumnName ="id")
    private Answer answer;
    
    @NotNull(message = "The participant must not be null")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "participant", referencedColumnName ="id")
    private Participant participant;
    
    public SurveyResponse(Question question, Answer answer, Participant participant) {
        this.question = question;
        this.answer = answer;
        this.participant = participant;
    }
}
