package com.ingsoftware.contacts.controllers;

import com.ingsoftware.contacts.models.dtos.ContactRequestDTO;
import com.ingsoftware.contacts.models.dtos.ContactResponseDTO;
import com.ingsoftware.contacts.models.entities.Contact;
import com.ingsoftware.contacts.services.interfaces.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact-management")
@Validated
public class ContactController {

  private final ContactService contactService;

  public ContactController(ContactService contactService) {
    this.contactService = contactService;
  }

  @Operation(
      summary = "Search through all contacts page by page",
      description = "Returns a list of contacts available and divide them to pages.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Returns a list of contacts paginated."),
        @ApiResponse(
            responseCode = "404",
            description = "Specified resource has not been found.",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error.",
            content = @Content)
      })
  @GetMapping("/users/contacts/paginated/page={page}/size={size}")
  public List<ContactResponseDTO> findAllPaginated(
      Pageable pageable, @PathVariable int page, @PathVariable int size) {
    pageable = PageRequest.of(page, size);
    return contactService.findAll(pageable);
  }

  @Operation(
      summary = "Search through user's contacts by first name",
      description =
          "Returns a list of contacts for specified user that are matching with a keyword typed based on first name of the contact.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a list of contacts that are matching."),
        @ApiResponse(
            responseCode = "404",
            description = "Specified resource has not been found.",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error.",
            content = @Content)
      })
  @GetMapping("/users/{tsidUser}/contacts/first-name={keyword}")
  public List<ContactResponseDTO> findAllByFirstNameKeyword(
      @PathVariable long tsidUser, @PathVariable @Size(min = 3, max = 15) String keyword) {
    return contactService.findAllByFirstNameKeyword(tsidUser, keyword);
  }

  @Operation(
      summary = "Search through user's contacts by last name",
      description =
          "Returns a list of contacts for specified user that are matching with a keyword typed based on last name of the contact.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a list of contacts that are matching."),
        @ApiResponse(
            responseCode = "404",
            description = "Specified resource has not been found.",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error.",
            content = @Content)
      })
  @GetMapping("/users/{tsidUser}/contacts/last-name={keyword}")
  public List<ContactResponseDTO> findAllByLastNameKeyword(
      @PathVariable long tsidUser, @PathVariable @Size(min = 3, max = 15) String keyword) {
    return contactService.findAllByLastNameKeyword(tsidUser, keyword);
  }

  @Operation(
      summary = "Search through user's contacts by phone number",
      description =
          "Returns a list of contacts that for specified user are matching with a keyword typed based on phone number of the contact.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a list of contacts that are matching."),
        @ApiResponse(
            responseCode = "404",
            description = "Specified resource has not been found.",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error.",
            content = @Content)
      })
  @GetMapping("/users/{tsidUser}/contacts/phone-number={keyword}")
  public List<ContactResponseDTO> findAllByPhoneNumberKeyword(
      @PathVariable long tsidUser, @PathVariable @Size(min = 3, max = 15) String keyword) {
    return contactService.findAllByPhoneNumberKeyword(tsidUser, keyword);
  }

  @Operation(
      summary = "Search through user's contacts by address",
      description =
          "Returns a list of contacts for specified user that are matching with a keyword typed based on address of the contact.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a list of contacts that are matching."),
        @ApiResponse(
            responseCode = "404",
            description = "Specified resource has not been found.",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error.",
            content = @Content)
      })
  @GetMapping("/users/{tsidUser}/contacts/address={keyword}")
  public List<ContactResponseDTO> findAllByAddressKeyword(
      @PathVariable long tsidUser, @PathVariable @Size(min = 3, max = 15) String keyword) {
    return contactService.findAllByAddressKeyword(tsidUser, keyword);
  }

  @Operation(
      summary = "Search through contacts for specified user",
      description = "Returns a list of contacts for a specified user.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a list of contacts for specified user."),
        @ApiResponse(
            responseCode = "404",
            description = "Specified resource has not been found.",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error.",
            content = @Content)
      })
  @GetMapping("/users/{tsidUser}/contacts")
  public List<ContactResponseDTO> findAllByUser(@PathVariable long tsidUser) {
    return contactService.findAllByUser(tsidUser);
  }


  @Operation(
          summary = "Add new contact",
          description = "Adds new contact with request body provided")
  @ApiResponses(
          value = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Returns successfully added contact "),
                  @ApiResponse(
                          responseCode = "500",
                          description = "Internal server error.",
                          content = @Content)
          })
  @PostMapping("/users/contacts")
  public Contact addContact(@RequestBody ContactRequestDTO contactRequestDTO) {
    return contactService.save(contactRequestDTO);
  }

  @Operation(
      summary = "Delete a single contact by tsid",
      description = "Deletes a single contact that has a matching tsid that user specified.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Deletes the specified contact and returns a successful message."),
        @ApiResponse(
            responseCode = "404",
            description = "Specified resource has not been found.",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error.",
            content = @Content)
      })
  @DeleteMapping("/users/contacts/{tsid}")
  public String deleteContact(@PathVariable long tsid) {
    return contactService.deleteByTsid(tsid);
  }
}
