package com.ingsoftware.contacts.controllers;

import com.ingsoftware.contacts.models.dtos.ContactRequestDTO;
import com.ingsoftware.contacts.models.dtos.ContactResponseDTO;
import com.ingsoftware.contacts.models.entities.Contact;
import com.ingsoftware.contacts.services.implementations.CsvService;
import com.ingsoftware.contacts.services.interfaces.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Size;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/contact-management")
@Validated
public class ContactController {

  private final ContactService contactService;

  private final CsvService csvService;

  public ContactController(ContactService contactService, CsvService csvService) {
    this.contactService = contactService;
    this.csvService = csvService;
  }

  @GetMapping("/export-contacts/{tsid}")
  public ResponseEntity<Resource> exportContacts(@PathVariable long tsid) throws IOException {
    List<ContactResponseDTO> contactResponseDTOS = contactService.findAllByUser(tsid);
    String filename = "contacts.csv";
    InputStreamResource resource = new InputStreamResource(csvService.load(contactResponseDTOS));

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        .contentType(MediaType.parseMediaType("application/csv"))
        .body(resource);
  }

  @PostMapping("/import-contacts")
  public ResponseEntity<String> uploadFile(
      @RequestParam("file") MultipartFile file, HttpSession session) {

    // Check if the file is not empty
    if (file.isEmpty()) {
      return ResponseEntity.badRequest().body("Uploaded file is empty!");
    }

    // Check if the file is in correct format (CSV)
    if (!FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("csv")) {
      return ResponseEntity.badRequest().body("Uploaded file must be in .csv format!");
    }

    // Fetch the logged user
    long tsidUser = (long) session.getAttribute("tsid");
    String message = csvService.importContacts(file, tsidUser);

    return ResponseEntity.ok("File uploaded successfully.\n\n" + message);
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
  @GetMapping("/contacts/first-name={keyword}")
  public List<ContactResponseDTO> findAllByFirstNameKeyword(
      @PathVariable @Size(min = 3, max = 15) String keyword, HttpSession session) {
    long tsidUser = (long) session.getAttribute("tsid");
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
  @GetMapping("/contacts/last-name={keyword}")
  public List<ContactResponseDTO> findAllByLastNameKeyword(
      @PathVariable @Size(min = 3, max = 15) String keyword, HttpSession session) {
    long tsidUser = (long) session.getAttribute("tsid");
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
  @GetMapping("/contacts/phone-number={keyword}")
  public List<ContactResponseDTO> findAllByPhoneNumberKeyword(
      @PathVariable @Size(min = 3, max = 15) String keyword, HttpSession session) {
    long tsidUser = (long) session.getAttribute("tsid");
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
  @GetMapping("/contacts/address={keyword}")
  public List<ContactResponseDTO> findAllByAddressKeyword(
      @PathVariable @Size(min = 3, max = 15) String keyword, HttpSession session) {
    long tsidUser = (long) session.getAttribute("tsid");
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
  @GetMapping("/contacts")
  public List<ContactResponseDTO> findAllByUser(HttpSession session) {
    long tsidUser = (long) session.getAttribute("tsid");
    return contactService.findAllByUser(tsidUser);
  }

  @Operation(
      summary = "Add new contact",
      description = "Adds new contact with request body provided")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Returns successfully added contact "),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error.",
            content = @Content)
      })
  @PostMapping("/contacts")
  public ContactResponseDTO addContact(
      @RequestBody ContactRequestDTO contactRequestDTO, HttpSession session) {
    return contactService.save(contactRequestDTO, session);
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
  @DeleteMapping("/contacts/{tsid}")
  public String deleteContact(@PathVariable long tsid) {
    return contactService.deleteByTsid(tsid);
  }
}
