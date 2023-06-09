package com.ingsoftware.contacts.security.auth;


import com.ingsoftware.contacts.models.dtos.LoginRequestDTO;
import com.ingsoftware.contacts.models.dtos.UserRegistrationDTO;
import com.ingsoftware.contacts.models.entities.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;



    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        return authenticationService.registerUser(userRegistrationDTO);
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(HttpServletRequest request, @RequestBody LoginRequestDTO loginRequestDTO) {
        return authenticationService.loginUser(request, loginRequestDTO);
    }

}
