package com.example.taskadserver.helpers;

import com.example.taskadserver.exceptions.AuthenticationFailureException;
import com.example.taskadserver.exceptions.AuthorizationException;
import com.example.taskadserver.exceptions.EntityNotFoundException;
import com.example.taskadserver.models.User;
import com.example.taskadserver.services.contracts.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHelper {
    public static final String AUTHENTICATION_FAILURE_MESSAGE = "Wrong username or password";
    private static final String INVALID_AUTHENTICATION_ERROR = "Invalid authentication.";
    private final UserService userService;

    @Autowired
    public AuthenticationHelper(UserService userService) {
        this.userService = userService;
    }

    public User tryGetUserWithSession(HttpSession session) {
        String currentUser = (String) session.getAttribute("currentUser");

        if (currentUser == null) {
            throw new AuthenticationFailureException("No user logged in");
        }

        return userService.getUserByUsername(currentUser);
    }

    public User verifyBlocked(String username) {
        try {
            return userService.getUserByUsername(username);
        } catch (EntityNotFoundException e) {
            throw new AuthenticationFailureException(AUTHENTICATION_FAILURE_MESSAGE);
        }
    }

    public User verifyAuthentication(String username, String password) {
        try {
            User user = userService.getUserByUsername(username);
            if (!user.getPassword().equals(password)) {
                throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
            }
            return user;
        } catch (EntityNotFoundException e) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }
    }
}
