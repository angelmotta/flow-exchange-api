package com.example.flowapi.rest;

import com.example.flowapi.rest.payload.SignupRequest;
import com.example.flowapi.user.User;
import com.example.flowapi.user.UserService;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/users")
    public User createUser(@Valid @RequestBody SignupRequest signupRequest) throws MethodArgumentNotValidException {
        System.out.println("Received body request:");
        System.out.println(signupRequest);

        // Create User Object
        User newUser = new User();
        newUser.setName(signupRequest.getUserInfo().getName());
        newUser.setDni(signupRequest.getUserInfo().getDni());
        newUser.setLastnameMain(signupRequest.getUserInfo().getLastName_main());
        newUser.setLastnameSecondary(signupRequest.getUserInfo().getLastName_secondary());
        newUser.setAddress(signupRequest.getUserInfo().getAddress());
        // TODO: Get Email from Token
        newUser.setEmail("angelmotta@gmail.com");
        // Additional userInfo
        String ROLE_USER = "customer";
        String STATE_USER = "registered";
        newUser.setRole(ROLE_USER);
        newUser.setState(STATE_USER);
        System.out.println("New User object created");
        System.out.println(newUser);

        // Save User in Database
        userService.saveUser(newUser);
        System.out.println("controller: new user record saved");
        System.out.println(newUser);

        return newUser;
    }

}
