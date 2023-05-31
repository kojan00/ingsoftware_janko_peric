package com.ingsoftware.contacts.controllers;

import com.ingsoftware.contacts.models.dtos.UserResponseDTO;
import com.ingsoftware.contacts.models.dtos.UserRegistrationDTO;
import com.ingsoftware.contacts.models.entities.User;
import com.ingsoftware.contacts.services.interfaces.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-management")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users")
  public List<UserResponseDTO> findAll() {
    return userService.findAll();
  }

  @GetMapping("/users/{id}")
  public UserResponseDTO findById(@PathVariable long id) {
    return userService.findById(id);
  }

  @PostMapping("/users")
  public User addUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
    return userService.save(userRegistrationDTO);
  }

  @PutMapping("/users")
  public User editUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
    return userService.save(userRegistrationDTO);
  }

  @DeleteMapping("/users/{id}")
  public String deleteById(@PathVariable long id) {
    return userService.deleteById(id);
  }
}
