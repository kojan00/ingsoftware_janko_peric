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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class UserServiceImplementation implements UserService {

  private final UserRepository userRepository;

  private final UserMapper userMapper;

  private final UserRegistrationMapper userRegistrationMapper;

  public UserServiceImplementation(
      UserRepository userRepository,
      UserMapper userMapper,
      UserRegistrationMapper userRegistrationMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.userRegistrationMapper = userRegistrationMapper;
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
  public UserResponseDTO findById(long id) {
    User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    UserResponseDTO userResponseDTO = userMapper.toDto(user);

    return userResponseDTO;
  }

  @Override
  public User save(UserRegistrationDTO userRegistrationDTO) {
    User user = userRegistrationMapper.toEntity(userRegistrationDTO);
    TSID tsid = TSID.fast();
    user.setId(tsid.toLong());
    return userRepository.save(user);
  }

  @Override
  public String deleteById(long id) {
    findById(id);
    userRepository.deleteById(id);
    return "User has been deleted successfully!";
  }
}
