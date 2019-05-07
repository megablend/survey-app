/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.datatransferobject;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    
    @NotNull(message = "The question must not be null")
    private Long questionId;
    
    @NotNull(message = "The answer must not be null")
    private Long answerId;
}
