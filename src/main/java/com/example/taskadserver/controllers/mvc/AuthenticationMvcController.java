package com.example.taskadserver.controllers.mvc;

import com.example.taskadserver.dto.LoginDto;
import com.example.taskadserver.exceptions.AuthenticationFailureException;
import com.example.taskadserver.exceptions.AuthorizationException;
import com.example.taskadserver.helpers.AuthenticationHelper;
import com.example.taskadserver.models.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthenticationMvcController {

    private final AuthenticationHelper authenticationHelper;

    public AuthenticationMvcController(AuthenticationHelper authenticationHelper) {
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {

        model.addAttribute("login", new LoginDto());
        return "LoginView";
    }

    @PostMapping("/login")
    public String handleLogin(@Valid @ModelAttribute("login") LoginDto loginDto,
                              BindingResult bindingResult, HttpSession session, Model model) {
        if (bindingResult.hasErrors()) {
            return "LoginView";
        }
        try {
            User user = authenticationHelper.verifyBlocked(loginDto.getUsername());
            String fullName = String.format("%s %s", user.getFirstName(), user.getLastName());
            session.setAttribute("fullName", fullName);
            session.setAttribute("currentUser", user.getUsername());
        } catch (AuthenticationFailureException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";

        }

        try {
            User user = authenticationHelper.verifyAuthentication(loginDto.getUsername(), loginDto.getPassword());
            session.setAttribute("currentUser", user.getUsername());
            return "redirect:/";
        } catch (AuthenticationFailureException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        } catch (AuthorizationException e) {
            bindingResult.rejectValue("username", "auth_error", e.getMessage());
            return "LoginView";
        }
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.removeAttribute("currentUser");
        return "redirect:/";
    }
}
