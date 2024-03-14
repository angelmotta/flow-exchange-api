package com.example.flowapi.rest;

import com.example.flowapi.exception.ResourceAlreadyExistException;
import com.example.flowapi.rest.payload.EmailAvailabilityRequest;
import com.example.flowapi.rest.payload.EmailAvailabilityResponse;
import com.example.flowapi.rest.payload.SignupRequest;
import com.example.flowapi.rest.payload.UserInfoSignupRequest;
import com.example.flowapi.user.User;
import com.example.flowapi.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService, JwtDecoder jwtDecoder) {
        this.userService = userService;
    }

    @PostMapping("/availability")
    public ResponseEntity<EmailAvailabilityResponse> checkEmailAvailability(Authentication authentication) {
        // Get userEmail from authentication
        String userEmail = getName(authentication).orElseThrow(() -> new AccessDeniedException("Invalid identity"));
        // Check if user is available
        Boolean isAvailable = userService.isAvailable(userEmail);
        if (!isAvailable) {
            throw new ResourceAlreadyExistException(String.format("User with email '%s' is already registered", userEmail));
        }

        // Response: email available
        System.out.println("Email available : " + userEmail);
        return ResponseEntity.ok(new EmailAvailabilityResponse(isAvailable));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserInfoSignupRequest userInfoSignupRequest, Authentication authentication) {
        System.out.println("Received body request:");
        System.out.println(userInfoSignupRequest);

        String userEmail = getName(authentication).orElseThrow(() -> new AccessDeniedException("Invalid identity"));
        Boolean isAvailable = userService.isAvailable(userEmail);
        System.out.println(userEmail + " available: " + isAvailable);
        if (!isAvailable) {
            throw new ResourceAlreadyExistException(String.format("User with email '%s' is already registered", userEmail));
        }

        // Create User Object
        User newUser = userService.create(userInfoSignupRequest, userEmail);
        System.out.println("controller: new user record saved");
        System.out.println(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }


    private static Optional<String> getName(Authentication authentication) {
        authentication.getName();
        return Optional.of(authentication.getPrincipal())
                .filter(Jwt.class::isInstance)
                .map(Jwt.class::cast)
                .map(Jwt::getClaims)
                .map(claims -> claims.get("email").toString());
    }
}
