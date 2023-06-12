package com.ingsoftware.contacts.controllers;

import com.ingsoftware.contacts.models.dtos.ContactDTO;
import com.ingsoftware.contacts.models.entities.Contact;
import com.ingsoftware.contacts.services.interfaces.ContactService;
import jakarta.validation.Valid;
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

  @GetMapping("/users/contacts/paginated/page={page}/size={size}")
  public List<ContactDTO> findAllPaginated(
      Pageable pageable, @PathVariable int page, @PathVariable int size) {
    pageable = PageRequest.of(page, size);
    return contactService.findAll(pageable);
  }

  @GetMapping("/users/{idUser}/contacts/first-name={keyword}")
  public List<ContactDTO> findAllByFirstNameKeyword(
      @PathVariable long idUser, @PathVariable @Size(min = 3, max = 15) String keyword) {
    return contactService.findAllByFirstNameKeyword(idUser, keyword);
  }

  @GetMapping("/users/{idUser}/contacts/last-name={keyword}")
  public List<ContactDTO> findAllByLastNameKeyword(
      @PathVariable long idUser, @PathVariable @Size(min = 3, max = 15) String keyword) {
    return contactService.findAllByLastNameKeyword(idUser, keyword);
  }

  @GetMapping("/users/{idUser}/contacts/phone-number={keyword}")
  public List<ContactDTO> findAllByPhoneNumberKeyword(
      @PathVariable long idUser, @PathVariable @Size(min = 3, max = 15) String keyword) {
    return contactService.findAllByPhoneNumberKeyword(idUser, keyword);
  }

  @GetMapping("/users/{idUser}/contacts/address={keyword}")
  public List<ContactDTO> findAllByAddressKeyword(
      @PathVariable long idUser, @PathVariable @Size(min = 3, max = 15) String keyword) {
    return contactService.findAllByAddressKeyword(idUser, keyword);
  }

  @GetMapping("/users/{idUser}/contacts")
  public List<ContactDTO> findAllByUser(@PathVariable long idUser) {
    return contactService.findAllByUser(idUser);
  }

  @GetMapping("/contacts/{idContact}")
  public ContactDTO findById(@PathVariable long idContact) {
    return contactService.findById(idContact);
  }

  @PostMapping("/users/{idUser}/contacts")
  public Contact addContact(@RequestBody @Valid ContactDTO contactDTO, @PathVariable long idUser) {
    return contactService.addContact(contactDTO, idUser);
  }

  @PutMapping("/users/{idUser}/contacts")
  public Contact editContact(@RequestBody ContactDTO contactDTO, @PathVariable long idUser) {
    return contactService.addContact(contactDTO, idUser);
  }

  @DeleteMapping("/users/contacts/{idContact}")
  public String deleteById(@PathVariable long idContact) {
    return contactService.deleteById(idContact);
  }
}
