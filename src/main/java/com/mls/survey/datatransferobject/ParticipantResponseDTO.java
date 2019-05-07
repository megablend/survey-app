/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.datatransferobject;

import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantResponseDTO {
    
    @Email(message = "Please provide a valid email address")
    private String email;
    
    @NotBlank(message = "The phone number must not be null")
    private String phoneNumber;
    
    @NotEmpty(message = "No response(s) provided")
    List<ResponseDTO> responses;
}
