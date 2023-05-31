package com.ingsoftware.contacts.services.implementations;

import com.ingsoftware.contacts.models.dtos.ContactDTO;
import com.ingsoftware.contacts.models.entities.Contact;
import com.ingsoftware.contacts.models.entities.User;
import com.ingsoftware.contacts.repositories.ContactRepository;
import com.ingsoftware.contacts.repositories.UserRepository;
import com.ingsoftware.contacts.services.interfaces.ContactService;
import com.ingsoftware.contacts.services.mappers.ContactMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ContactServiceImplementation implements ContactService {

  private final ContactRepository contactRepository;
  private final UserRepository userRepository;

  private final ContactMapper contactMapper;

  public ContactServiceImplementation(
      ContactRepository contactRepository,
      UserRepository userRepository,
      ContactMapper contactMapper) {
    this.contactRepository = contactRepository;
    this.userRepository = userRepository;
    this.contactMapper = contactMapper;
  }

  @Override
  public List<ContactDTO> findAll() {
    List<Contact> contacts = contactRepository.findAll();
    List<ContactDTO> contactDTOS = contactMapper.toDtolist(contacts);
    return contactDTOS;
  }

  @Override
  public List<ContactDTO> findAllByUser(int id) {
    List<Contact> contacts = contactRepository.findAllByUser(id);
    List<ContactDTO> contactDTOS = contactMapper.toDtolist(contacts);
    return contactDTOS;
  }

  @Override
  public ContactDTO findById(int id) {
    Contact contact =
        contactRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        NOT_FOUND, "Specified resource has not been found."));
    ContactDTO contactDTO = contactMapper.toDto(contact);
    return contactDTO;
  }

  @Override
  public Contact addContact(Contact contact, int idUser) {
    User user =
        userRepository
            .findById(idUser)
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        NOT_FOUND, "Specified resource has not been found."));
    contact.setUser(user);
    return contactRepository.save(contact);
  }

  @Override
  public String deleteById(int id) {
    findById(id);
    contactRepository.deleteById(id);
    return "Contact has been successfully deleted!";
  }
}
