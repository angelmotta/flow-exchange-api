package com.example.flowapi.rest.controlleradvice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        System.out.println("Handler onConstraintValidationException in action");
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            errorResponse.getViolations().add(new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return errorResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValid(MethodArgumentNotValidException e) {
        System.out.println("Handler onMethodArgumentNotValid in action");
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorResponse.getViolations().add(new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return errorResponse;
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintSQLValidationException(SQLException e) {
        System.out.println("Handler SQLException");
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        errorResponse.getViolations().add(new Violation(e.getSQLState(), e.getMessage()));
        return errorResponse;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        System.out.println("Handle DataIntegrityViolationException");
        // Check if the exception message contains the unique constraint violation error
        if (ex.getMessage().contains("duplicate key value violates unique constraint")) {
            //return new ResponseEntity<>("Duplicate key value violates unique constraint.", HttpStatus.CONFLICT);
            GeneralErrorResponse errorResponse = new GeneralErrorResponse("Duplicate key value violates unique constraint.");
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }
        // Handle other data integrity violations here
        return new ResponseEntity<>("Data integrity violation occurred.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        System.out.println("Default Exception Handler");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred: " + ex.getMessage());
    }
}
