/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.domainobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Entity
@Table(name = "participants", indexes = {
    @Index(columnList = "email", name = "idx_email_participant")
})
@Data
@NoArgsConstructor
public class Participant extends DateCreated {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Email(message = "Provide a valid email")
    @Column(nullable = false)
    private String email;
    
    @NotBlank(message = "Please provide the phone number")
    @Column(nullable = false)
    private String phoneNumber;
    
    public Participant(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
