/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.dataaccessobject;

import com.mls.survey.domainobject.Answer;
import com.mls.survey.domainobject.Question;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public interface AnswerRepo extends JpaRepository<Answer, Long> {
    
    Optional<Answer> findByIdAndDeleted(long id, boolean deleted);
    
    @Modifying
    @Query("UPDATE Answer a SET a.deleted = true WHERE a.question = ?1")
    void deleteAnswers(Question question);
}
