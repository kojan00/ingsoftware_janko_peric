package com.ingsoftware.contacts.controllers;

import com.ingsoftware.contacts.models.dtos.UserResponseDTO;
import com.ingsoftware.contacts.models.dtos.UserRegistrationDTO;
import com.ingsoftware.contacts.models.entities.User;
import com.ingsoftware.contacts.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
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

  // note: sort sam odradio ovako za sad cisto da vidim da li funkcionise, pretpostavljam da cemo
  // posebne metode pisati za posebne pretrage i tu definisati sortove
  @GetMapping("/users/paginated/page={page}/size={size}/sort={sort}")
  public List<UserResponseDTO> findAllPaginated(
      Pageable pageable,
      @PathVariable int page,
      @PathVariable int size,
      @PathVariable String sort) {
    pageable = PageRequest.of(page, size, Sort.by(sort));
    return userService.findAllPaginated(pageable);
  }

  @GetMapping("/users/{id}")
  public UserResponseDTO findById(@PathVariable long id) {
    return userService.findById(id);
  }

  @PostMapping("/users")
  public User addUser(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) {
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
