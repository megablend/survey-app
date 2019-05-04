/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.dataaccessobject;

import com.mls.survey.domainobject.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public interface AnswerRepo extends JpaRepository<Answer, Long> {
    
}
