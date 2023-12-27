package com.example.flowapi.rest;

import com.example.flowapi.rest.payload.SignupRequest;
import com.example.flowapi.rest.payload.UserInfoSignupRequest;
import com.example.flowapi.user.User;
import jakarta.validation.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class UserController {

    @PostMapping("/users")
    public SignupRequest createUser(@Valid @RequestBody SignupRequest signupRequest) throws MethodArgumentNotValidException {
        System.out.println("Received body request:");
        System.out.println(signupRequest);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        UserInfoSignupRequest r = signupRequest.getUserInfo();

        BindingResult bindingResult = new BeanPropertyBindingResult(r, "UserInfoSignupRequest");
        if (r == null) {
            bindingResult.addError(new FieldError("UserInfo", "UserInfo", "UserInfo cannot be null"));
        }
        if (bindingResult.hasErrors()) {
            System.out.println("errors in validation");
            throw new MethodArgumentNotValidException(null, bindingResult);
        }

        Set<ConstraintViolation<UserInfoSignupRequest>> violations = validator.validate(r);
        if (!violations.isEmpty()) {
            System.out.println("Validation error detected");
            throw new ConstraintViolationException(violations);
        }

        System.out.println("Validation successful");
        return signupRequest;
    }

}
