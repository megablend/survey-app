/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.dataaccessobject;

import com.mls.survey.domainobject.Question;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public interface QuestionRepo extends JpaRepository<Question, Long> {
    
    Optional<Question> findByIdAndDeleted(long id, boolean deleted);
}
