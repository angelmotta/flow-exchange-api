package com.example.flowapi.rest;

import com.example.flowapi.rest.payload.SignupRequest;
import com.example.flowapi.rest.payload.UserInfoSignupRequest;
import com.example.flowapi.user.User;
import jakarta.validation.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.MethodParameter;
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
    public User createUser(@Valid @RequestBody SignupRequest signupRequest) throws MethodArgumentNotValidException {
        System.out.println("Received body request:");
        System.out.println(signupRequest);


        UserInfoSignupRequest userInfo = null;
        if (signupRequest.getStep().equals("2")) {
            userInfo = signupRequest.getUserInfo();
            if (userInfo == null) {
                BindingResult bindingResult = new BeanPropertyBindingResult(userInfo, "UserInfoSignupRequest");
                bindingResult.addError(new FieldError("UserInfo", "user_info", "user_info Object field required"));
                throw new MethodArgumentNotValidException(null, bindingResult);
            }
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<UserInfoSignupRequest>> violations = validator.validate(userInfo);
        if (!violations.isEmpty()) {
            System.out.println("Validation error detected");
            throw new ConstraintViolationException(violations);
        }


        System.out.println("Validation successful:");
        System.out.println(userInfo);

        // Create User Object
        User newUser = new User();
        newUser.setName(signupRequest.getUserInfo().getName());
        newUser.setDni(signupRequest.getUserInfo().getDni());
        newUser.setLastnameMain(signupRequest.getUserInfo().getLastName_main());
        newUser.setLastnameSecondary(signupRequest.getUserInfo().getLastName_secondary());
        newUser.setAddress(signupRequest.getUserInfo().getAddress());
        // Additional userInfo
        String ROLE_USER = "customer";
        String STATE_USER = "registered";
        newUser.setRole(ROLE_USER);
        newUser.setState(STATE_USER);
        System.out.println(newUser);

        return newUser;
    }

}
