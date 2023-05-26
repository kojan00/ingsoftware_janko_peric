package com.ingsoftware.contacts.controllers;

import com.ingsoftware.contacts.models.dtos.UserDTO;
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
  public List<UserDTO> findAll() {
    return userService.findAll();
  }

  @GetMapping("/users/{id}")
  public UserDTO findById(@PathVariable int id) {
    return userService.findById(id);
  }

  @PostMapping("/users")
  public User addUser(@RequestBody User user) {
    return userService.save(user);
  }

  @PutMapping("/users")
  public User editUser(@RequestBody User user) {
    return userService.save(user);
  }

  @DeleteMapping("/users/{id}")
  public String deleteById(@PathVariable int id) {
    return userService.deleteById(id);
  }
}
