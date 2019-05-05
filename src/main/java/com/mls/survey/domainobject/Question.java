/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.domainobject;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Entity
@Table(name = "questions", indexes = {
    @Index(columnList = "question", name = "idx_question")
})
@Data
@NoArgsConstructor
public class Question extends DateCreated {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @NotBlank(message = "Question not provided")
    @Column(nullable = false, unique = true)
    private String  question;
    
    @OneToMany(mappedBy = "question")
    private List<Answer> answers;
    
    public Question(String question) {
        this.question = question;
    }
}
