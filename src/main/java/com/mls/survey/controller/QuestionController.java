/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@RestController
@RequestMapping("v1/questions")
public class QuestionController {
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuestion() {
        
    }
}
