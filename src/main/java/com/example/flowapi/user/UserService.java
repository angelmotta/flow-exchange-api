package com.example.flowapi.user;

import com.example.flowapi.rest.payload.SignupRequest;
import com.example.flowapi.rest.payload.UserInfoSignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        userRepository.save(user);
        System.out.println("User successfully saved in database");
        return user;
    }

    public User create(UserInfoSignupRequest userInfoSignupRequest, String userEmail) {
        User newUser = new User();
        newUser.setName(userInfoSignupRequest.getName());
        newUser.setDni(userInfoSignupRequest.getDni());
        newUser.setLastnameMain(userInfoSignupRequest.getLastName_main());
        newUser.setLastnameSecondary(userInfoSignupRequest.getLastName_secondary());
        newUser.setAddress(userInfoSignupRequest.getAddress());
        newUser.setEmail(userEmail);
        // Additional userInfo
        String ROLE_USER = "customer";
        String STATE_USER = "registered";
        newUser.setRole(ROLE_USER);
        newUser.setState(STATE_USER);

        return saveUser(newUser);
    }

    public Optional<User> get(String email) {
        return userRepository.findUserByEmail(email);
    }

    public Boolean isAvailable(String email) {
        Optional<User> userRetrieved = get(email);
        return userRetrieved.isEmpty();
    }
}
