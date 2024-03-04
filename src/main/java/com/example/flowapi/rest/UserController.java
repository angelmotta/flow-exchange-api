package com.example.flowapi.rest;

import com.example.flowapi.exception.ResourceAlreadyExistException;
import com.example.flowapi.rest.payload.EmailAvailabilityRequest;
import com.example.flowapi.rest.payload.EmailAvailabilityResponse;
import com.example.flowapi.rest.payload.SignupRequest;
import com.example.flowapi.user.User;
import com.example.flowapi.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final JwtDecoder jwtDecoder;

    @Autowired
    public UserController(UserService userService, JwtDecoder jwtDecoder) {
        this.userService = userService;
        this.jwtDecoder = jwtDecoder;
    }

    @PostMapping("/availability")
    public ResponseEntity<EmailAvailabilityResponse> checkEmailAvailability(@Valid @RequestBody EmailAvailabilityRequest emailAvailabilityRequest, HttpServletRequest request) {
        // Validate Header
        System.out.println("Received body request:");
        System.out.println(emailAvailabilityRequest);

        // Authenticate request
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("--- Authorization header ----");
        System.out.println(authorizationHeader);
        System.out.println("-------------");
        if (authorizationHeader == null) {
            // Todo: 400 status
            throw new AccessDeniedException("Invalid Authorization Header: null");
        }
        String[] authHeaderList = authorizationHeader.split(" ");
        if (authHeaderList.length != 2 || authHeaderList[1] == null) {
            throw new AccessDeniedException("Invalid Authorization Header: should ne 'Bearer <token>'");
        }

        // Todo: try / 401 not authorized
        Jwt jwt = jwtDecoder.decode(authHeaderList[1]);
        var claims = jwt.getClaims();
        String userEmail = claims.get("email").toString();

        // Todo: if request is step 1: validate if email is available and return result
        // Todo: return result: 200 (available) | 409 (user already exist)
        Boolean isAvailable = userService.isAvailable(userEmail);
        System.out.println("Is user available?");
        System.out.println(isAvailable);
//        if (!isAvailable) {
//            throw new ResourceAlreadyExistException(String.format("User with email '%s' is already registered", userEmail));
//        }
        return ResponseEntity.ok(new EmailAvailabilityResponse(isAvailable));
    }


    @PostMapping
    public User createUser(@Valid @RequestBody SignupRequest signupRequest, HttpServletRequest request) {
        System.out.println("Received body request:");
        System.out.println(signupRequest);

        // Authenticate request
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("--- Authorization header ----");
        System.out.println(authorizationHeader);
        System.out.println("-------------");
        if (authorizationHeader == null) {
            // Todo: 400 status
            throw new AccessDeniedException("Invalid Authorization Header: null");
        }
        String[] authHeaderList = authorizationHeader.split(" ");
        if (authHeaderList.length != 2 || authHeaderList[1] == null) {
            throw new AccessDeniedException("Invalid Authorization Header: should ne 'Bearer <token>'");
        }

        // Todo: try / 401 not authorized
        Jwt jwt = jwtDecoder.decode(authHeaderList[1]);
        var claims = jwt.getClaims();
        String userEmail = claims.get("email").toString();

        // Todo: if request is step 1: validate if email is available and return result
        // Todo: return result: 200 (available) | 409 (user already exist)
        Boolean isAvailable = userService.isAvailable(userEmail);
        System.out.println("Is user available?");
        System.out.println(isAvailable);
        if (!isAvailable) {
            throw new ResourceAlreadyExistException(String.format("User with email '%s' is already registered", userEmail));
        }
        /*
        * if (!isAvailable) {
        *   return 409
        * } else {
        *   if (step == 1) {
        *     return 200
        *   }
        * }
        * */

        // Create User Object
        User newUser = userService.create(signupRequest, userEmail);

        System.out.println("controller: new user record saved");
        System.out.println(newUser);
        // Todo: Status code 201 (resource created)
        return newUser;
    }

}
