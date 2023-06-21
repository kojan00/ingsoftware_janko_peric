package com.ingsoftware.contacts.services.implementations;

import com.ingsoftware.contacts.exceptions.UserNotFoundException;
import com.ingsoftware.contacts.models.dtos.UserResponseDTO;
import com.ingsoftware.contacts.models.dtos.UserRegistrationDTO;
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

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

  private final UserRepository userRepository;

  private final UserMapper userMapper;

  private final UserRegistrationMapper userRegistrationMapper;

  private final PasswordEncoder passwordEncoder;

  public UserServiceImplementation(
      UserRepository userRepository,
      UserMapper userMapper,
      UserRegistrationMapper userRegistrationMapper,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.userRegistrationMapper = userRegistrationMapper;
    this.passwordEncoder = passwordEncoder;
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
  public UserResponseDTO save(UserRegistrationDTO userRegistrationDTO) {
    User user = userRegistrationMapper.toEntity(userRegistrationDTO);

    TSID tsid = TSID.fast();
    user.setTsid(tsid.toLong());

    String encodedPassword = passwordEncoder.encode(userRegistrationDTO.password());
    user.setPassword(encodedPassword);
    userRepository.save(user);
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
