package com.example.flowapi.rest.controlleradvice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        System.out.println("onConstraintValidationException in action");
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
        System.out.println("onMethodArgumentNotValid in action");
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorResponse.getViolations().add(new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return errorResponse;
    }
}
