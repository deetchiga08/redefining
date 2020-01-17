package com.briller.exception;

import com.briller.Response.ftdcResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unexpectedExceptionHandler(Exception e){
        return new ResponseEntity<>(getErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?>  unAuthorizedExceptionHandler(BadCredentialsException e){
        return new ResponseEntity<>(getErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?>  noHandlerFoundException(NoHandlerFoundException e){
        return new ResponseEntity<>(getErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?>  entityNotFoundException(EntityNotFoundException e){
        return new ResponseEntity<>(getErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?>  userAlreadyExistsException(UserAlreadyExistsException e){
        return new ResponseEntity<>(getErrorResponse(e.getMessage()), HttpStatus.CONFLICT);
    }

    private ftdcResponse getErrorResponse(String error){
        return new ftdcResponse(0, "Failure", error);
    }

}
