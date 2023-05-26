package com.ingsoftware.contacts.services.implementations;

import com.ingsoftware.contacts.models.dtos.UserDTO;
import com.ingsoftware.contacts.models.entities.User;
import com.ingsoftware.contacts.repositories.UserRepository;
import com.ingsoftware.contacts.services.interfaces.UserService;
import com.ingsoftware.contacts.services.mappers.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class UserServiceImplementation implements UserService {

  private final UserRepository userRepository;

  private final UserMapper userMapper;

  public UserServiceImplementation(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  @Override
  public List<UserDTO> findAll() {
    List<User> users = userRepository.findAll();
    List<UserDTO> userDTOS = userMapper.toDtoList(users);
    return userDTOS;
  }

  @Override
  public UserDTO findById(int id) {
    User user = userRepository
            .findById(id)
            .orElseThrow(
                    () -> new ResponseStatusException(NOT_FOUND, "Specified resource has not been found."));
    UserDTO userDTO = userMapper.toDto(user);
    return userDTO;
  }

  @Override
  public User save(User user) {
    return userRepository.save(user);
  }

  @Override
  public String deleteById(int id) {
    findById(id);
    userRepository.deleteById(id);
    return "User has been deleted successfully!";
  }
}
