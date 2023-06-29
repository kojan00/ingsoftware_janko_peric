package com.ingsoftware.contacts.controllers;

import com.ingsoftware.contacts.models.dtos.UserResponseDTO;
import com.ingsoftware.contacts.models.dtos.UserRegistrationDTO;
import com.ingsoftware.contacts.services.implementations.PhoneService;
import com.ingsoftware.contacts.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-management")
@Validated
public class UserController {

  private final UserService userService;

  private final PhoneService phoneService;

  public UserController(UserService userService, PhoneService phoneService) {
    this.userService = userService;
    this.phoneService = phoneService;
  }

  @GetMapping("/verify-email/{email}")
  public String verifyEmail(@PathVariable String email) {
    String message = userService.verifyEmail(email);
    return message;
  }

  @GetMapping("/verify-phone/generate-verification-code")
  public ResponseEntity generatePhoneVerificationCode(HttpSession session) {
    long tsid = (long) session.getAttribute("tsid");

    return phoneService.generateVerificationCode(tsid);
  }

  @GetMapping("/verify-phone/verify-code/{code}")
  public ResponseEntity verifyPhoneVerificationCode(@PathVariable String code, HttpSession session) {
    long tsid = (long) session.getAttribute("tsid");

    return phoneService.verifyCode(tsid, code);
  }

  @GetMapping("/users")
  public List<UserResponseDTO> findAll() {
    return userService.findAll();
  }

  // note: sort sam odradio ovako za sad cisto da vidim da li funkcionise, pretpostavljam da cemo
  // posebne metode pisat
  // i za posebne pretrage i tu definisati sortove

  @Operation(
      summary = "Search through all users page by page",
      description = "Returns a list of users available and divide them to pages with chosen size.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Returns a list of users paginated."),
        @ApiResponse(
            responseCode = "404",
            description = "Specified resource has not been found.",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error.",
            content = @Content)
      })
  @GetMapping("/users/paginated/page={page}/size={size}/sort={sort}")
  public List<UserResponseDTO> findAllPaginated(
      Pageable pageable,
      @PathVariable int page,
      @PathVariable int size,
      @PathVariable String sort) {
    pageable = PageRequest.of(page, size, Sort.by(sort));
    return userService.findAllPaginated(pageable);
  }

  @Operation(
      summary = "Search for user with specified TSID",
      description = "Returns a single user that matches specified TSID.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a single user that matches specified TSID."),
        @ApiResponse(
            responseCode = "404",
            description = "Specified resource has not been found.",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error.",
            content = @Content)
      })
  @GetMapping("/users/{tsid}")
  public UserResponseDTO findByTsid(@PathVariable long tsid) {
    return userService.findByTsid(tsid);
  }

  @Operation(summary = "Add new user. ", description = "Adds new user with request body provided.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Returns successfully added user "),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error.",
            content = @Content)
      })
  @PostMapping("/users")
  public UserResponseDTO addUser(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) {
    return userService.save(userRegistrationDTO);
  }

  @Operation(
      summary = "Edit existing user. ",
      description =
          "Edit the existing user with request body provided for what needs to be changed.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Returns successfully edited user "),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error.",
            content = @Content)
      })
  @PutMapping("/users")
  public UserResponseDTO editUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
    return userService.save(userRegistrationDTO);
  }

  @Operation(
      summary = "Delete a single user by his tsid",
      description = "Deletes a single user that has a matching tsid that admin has specified.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Deletes the specified user and returns a successful message."),
        @ApiResponse(
            responseCode = "404",
            description = "Specified resource has not been found.",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error.",
            content = @Content)
      })
  @DeleteMapping("/users/{tsid}")
  public String deleteByTsid(@PathVariable long tsid) {
    return userService.deleteByTsid(tsid);
  }
}
