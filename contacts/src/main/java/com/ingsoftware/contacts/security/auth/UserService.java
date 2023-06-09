package com.ingsoftware.contacts.security.auth;

import com.ingsoftware.contacts.models.entities.User;
import com.ingsoftware.contacts.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private final PasswordEncoder encoder;

  private final UserRepository userRepository;

  public UserService(PasswordEncoder encoder, UserRepository userRepository) {
    this.encoder = encoder;
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) {
    User user = userRepository.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException("Bad credentials");
    }
    return userRepository.findByEmail(email);
  }
}
