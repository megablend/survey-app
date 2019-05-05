/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mls.survey.advice;

import com.mls.survey.datatransferobject.ErrorDTO;
import com.mls.survey.exception.ConstraintsViolationException;
import com.mls.survey.exception.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@ControllerAdvice
@Slf4j
public class ErrorHandler extends ResponseEntityExceptionHandler {
    
    /**
     * Handle ConstraintViolationException 
     * @param ex
     * @param request
     * @return 
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException ex, final WebRequest request) {
        Set<String> errors = ex.getConstraintViolations().stream().map(c -> c.getMessage()).collect(toSet());
        return handleExceptionInternal(ex, new ErrorDTO(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    /**
     * Handle TransactionException 
     * @param ex
     * @param request
     * @return 
     */
    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<Object> handleTransactionException(final TransactionException ex, final WebRequest request) {
        String bodyResponse = "Unable to complete the requested operation because the entity exists or an exception occurred";
        return handleExceptionInternal(ex, new ErrorDTO(bodyResponse), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    /**
     * Handle ConstraintsViolationException and EntityNotFoundException
     * @param ex
     * @param request
     * @return 
     */
    @ExceptionHandler({ConstraintsViolationException.class, EntityNotFoundException.class})
    public ResponseEntity<Object> handleConstraintsViolation(final Exception ex, final WebRequest request) {
        return handleExceptionInternal(ex, new ErrorDTO(ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    /**
     * Override the handling of MethodArgumentNotValidException
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return 
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                                                                    .map(e -> e.getDefaultMessage())
                                                                    .collect(toList());
        return handleExceptionInternal(ex, new ErrorDTO(errors), headers, HttpStatus.BAD_REQUEST, request);
    }
    
    /**
     * Manage exceptions
     * @param ex
     * @param request
     * @return 
     */
    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
    public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
        log.error("Exception occurred", ex);
        final String bodyResponse = "System error, please try again.";
        return handleExceptionInternal(ex, new ErrorDTO(bodyResponse), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
