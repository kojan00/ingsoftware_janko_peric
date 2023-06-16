package com.ingsoftware.contacts.controllers;

import com.ingsoftware.contacts.models.dtos.ContactTypeDTO;
import com.ingsoftware.contacts.models.entities.ContactType;
import com.ingsoftware.contacts.services.interfaces.ContactTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact-types")
public class ContactTypeController {

  private final ContactTypeService contactTypeService;

  public ContactTypeController(ContactTypeService contactTypeService) {
    this.contactTypeService = contactTypeService;
  }

  @Operation(
      summary = "Search for contact type with specified TSID",
      description = "Returns a single contact type that matches specified TSID.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a single contact type that matches specified TSID."),
        @ApiResponse(
            responseCode = "404",
            description = "Specified resource has not been found.",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error.",
            content = @Content)
      })
  @GetMapping("/{tsid}")
  public ContactTypeDTO findByTsid(@PathVariable long tsid) {
    return contactTypeService.findByTsid(tsid);
  }

  @Operation(
      summary = "Add new contact type",
      description = "Adds new contact type with request body provided")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Returns successfully added contact type"),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error.",
            content = @Content)
      })
  @PostMapping("/add")
  public ContactType addContactType(@RequestBody ContactTypeDTO contactTypeDTO) {
    return contactTypeService.save(contactTypeDTO);
  }

  @Operation(
      summary = "Edit existing contact type. ",
      description =
          "Edit the existing contact type with request body provided for what needs to be changed.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns successfully edited contact type"),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error.",
            content = @Content)
      })
  @PutMapping("/{tsid}/update/new-type={type}")
  public String updateContactType(@PathVariable String type, @PathVariable long tsid) {
    contactTypeService.updateContactType(type, tsid);
    return "Contact updated successfully.";
  }
}
