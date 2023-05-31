package com.ingsoftware.contacts.controllers;

import com.ingsoftware.contacts.models.dtos.ContactDTO;
import com.ingsoftware.contacts.models.entities.Contact;
import com.ingsoftware.contacts.services.interfaces.ContactService;
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

  @GetMapping("/users/{idUser}/contacts/all")
  public List<ContactDTO> findAllByUser(@PathVariable long idUser) {
    return contactService.findAllByUser(idUser);
  }

  @GetMapping("/contacts/{idContact}")
  public ContactDTO findById(@PathVariable long idContact) {
    return contactService.findById(idContact);
  }

  @PostMapping("/users/{idUser}/contacts")
  public Contact addContact(@RequestBody ContactDTO contactDTO, @PathVariable long idUser) {
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
