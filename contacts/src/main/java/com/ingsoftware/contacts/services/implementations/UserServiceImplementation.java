package com.ingsoftware.contacts.services.implementations;

import com.ingsoftware.contacts.exceptions.UserNotFoundException;
import com.ingsoftware.contacts.models.dtos.UserResponseDTO;
import com.ingsoftware.contacts.models.dtos.UserRegistrationDTO;
import com.ingsoftware.contacts.models.entities.Role;
import com.ingsoftware.contacts.models.entities.User;
import com.ingsoftware.contacts.repositories.UserRepository;
import com.ingsoftware.contacts.services.interfaces.UserService;
import com.ingsoftware.contacts.services.mappers.UserMapper;
import com.ingsoftware.contacts.services.mappers.UserRegistrationMapper;
import io.hypersistence.tsid.TSID;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

  private final UserRepository userRepository;

  private final UserMapper userMapper;

  private final UserRegistrationMapper userRegistrationMapper;

  private final PasswordEncoder passwordEncoder;

  private final EmailService emailService;

  public UserServiceImplementation(
      UserRepository userRepository,
      UserMapper userMapper,
      UserRegistrationMapper userRegistrationMapper,
      PasswordEncoder passwordEncoder,
      EmailService emailService) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.userRegistrationMapper = userRegistrationMapper;
    this.passwordEncoder = passwordEncoder;
    this.emailService = emailService;
  }

  @Override
  public List<UserResponseDTO> findAll() {
    List<User> users = userRepository.findAll();
    List<UserResponseDTO> userResponseDTOS = userMapper.toDtoList(users);
    return userResponseDTOS;
  }

  @Override
  public List<UserResponseDTO> findAllPaginated(Pageable pageable) {
    List<User> users = userRepository.findAll(pageable).toList();
    List<UserResponseDTO> userResponseDTOS = userMapper.toDtoList(users);

    return userResponseDTOS;
  }

  @Override
  public UserResponseDTO findByTsid(long tsid) {
    User user = userRepository.findByTsid(tsid);
    if (user == null) {
      throw new UserNotFoundException();
    }
    UserResponseDTO userResponseDTO = userMapper.toDto(user);

    return userResponseDTO;
  }

  @Override
  public String verifyEmail(String email) {
    User user = userRepository.findByEmail(email);

    if (user == null) {
      return "Email verification failed.";
    }
    user.setEmailVerified(true);
    userRepository.save(user);
    return "Email successfully verified.";
  }

  @Override
  public UserResponseDTO save(UserRegistrationDTO userRegistrationDTO) {
    User user = userRegistrationMapper.toEntity(userRegistrationDTO);

    TSID tsid = TSID.fast();
    user.setTsid(tsid.toLong());

    String encodedPassword = passwordEncoder.encode(userRegistrationDTO.password());
    user.setPassword(encodedPassword);

    if (user.getRole() == Role.ADMIN) {
      user.setEmailVerified(true);
    }

    userRepository.save(user);

    // send the verification email
    try {
      emailService.sendMail(user);
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    } catch (jakarta.mail.MessagingException e) {
      throw new RuntimeException(e);
    }

    return userMapper.toDto(user);
  }

  @Override
  @Transactional
  public String deleteByTsid(long tsid) {
    UserResponseDTO userResponseDTO = findByTsid(tsid);
    userRepository.deleteByTsid(tsid);
    return "User has been deleted successfully!";
  }
}
