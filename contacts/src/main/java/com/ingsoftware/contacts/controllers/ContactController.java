package com.ingsoftware.contacts.controllers;

import com.ingsoftware.contacts.models.dtos.ContactDTO;
import com.ingsoftware.contacts.models.entities.Contact;
import com.ingsoftware.contacts.services.interfaces.ContactService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact-management")
public class ContactController {

  private final ContactService contactService;

  public ContactController(ContactService contactService) {
    this.contactService = contactService;
  }

  @GetMapping("/contacts")
  public List<ContactDTO> findAll() {
    return contactService.findAll();
  }

  @GetMapping("/users/contacts/paginated/page={page}/size={size}")
  public List<ContactDTO> findAllPaginated(
      Pageable pageable, @PathVariable int page, @PathVariable int size) {
    pageable = PageRequest.of(page, size);
    return contactService.findAll(pageable);
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
