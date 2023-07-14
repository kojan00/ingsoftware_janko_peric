package com.ingsoftware.contacts.security.auth;

import com.ingsoftware.contacts.models.dtos.LoginRequestDTO;
import com.ingsoftware.contacts.models.entities.User;
import com.ingsoftware.contacts.repositories.UserRepository;
import com.ingsoftware.contacts.services.mappers.UserRegistrationMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Transactional
public class AuthenticationService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final UserRegistrationMapper userMapper;

  private final AuthenticationManager authenticationManager;

  public AuthenticationService(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      UserRegistrationMapper userMapper,
      AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.userMapper = userMapper;
    this.authenticationManager = authenticationManager;
  }

  public ResponseEntity loginUser(HttpServletRequest request, LoginRequestDTO loginRequestDTO) {
    try {
      Authentication authenticate =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  loginRequestDTO.email(), loginRequestDTO.password()));

      User user = userRepository.findByEmail(loginRequestDTO.email());
      long tsid = user.getTsid();

      SecurityContext sc = SecurityContextHolder.getContext();
      sc.setAuthentication(authenticate);
      HttpSession session = request.getSession(true);

      session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
      session.setAttribute("tsid", tsid);

      String message = "Login successful!";

      HashMap<String, Object> response = new HashMap<>();
      response.put("message", message);
      response.put("tsid", String.valueOf(tsid));

      return ResponseEntity.ok().body(response);
    } catch (BadCredentialsException be) {
      return ResponseEntity.status(401).body("Bad credentials");
    }
  }
}
