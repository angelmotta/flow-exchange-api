package com.example.flowapi.rest;

import com.example.flowapi.rest.payload.SignupRequest;
import com.example.flowapi.user.User;
import com.example.flowapi.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;
    private final JwtDecoder jwtDecoder;

    @Autowired
    public UserController(UserService userService, JwtDecoder jwtDecoder) {
        this.userService = userService;
        this.jwtDecoder = jwtDecoder;
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody SignupRequest signupRequest, HttpServletRequest request) {
        System.out.println("Received body request:");
        System.out.println(signupRequest);

        // Authenticate request
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("--- Authorization header ----");
        System.out.println(authorizationHeader);
        System.out.println("-------------");
        if (authorizationHeader == null) {
            throw new AccessDeniedException("Invalid Authorization Header: null");
        }
        String[] authHeaderList = authorizationHeader.split(" ");
        if (authHeaderList.length != 2 || authHeaderList[1] == null) {
            throw new AccessDeniedException("Invalid Authorization Header: should ne 'Bearer <token>'");
        }

        Jwt jwt = jwtDecoder.decode(authHeaderList[1]);
        var claims = jwt.getClaims();
        String userEmail = claims.get("email").toString();

        // Create User Object
        User newUser = userService.registerNewUser(signupRequest, userEmail);

        // Save User in Database
        userService.saveUser(newUser);
        System.out.println("controller: new user record saved");
        System.out.println(newUser);

        return newUser;
    }

}
