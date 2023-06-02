package com.ingsoftware.contacts.services.implementations;

import com.ingsoftware.contacts.exceptions.ContactNotFoundException;
import com.ingsoftware.contacts.exceptions.UserNotFoundException;
import com.ingsoftware.contacts.models.dtos.ContactDTO;
import com.ingsoftware.contacts.models.dtos.UserResponseDTO;
import com.ingsoftware.contacts.models.entities.Contact;
import com.ingsoftware.contacts.models.entities.User;
import com.ingsoftware.contacts.repositories.ContactRepository;
import com.ingsoftware.contacts.repositories.UserRepository;
import com.ingsoftware.contacts.services.interfaces.ContactService;
import com.ingsoftware.contacts.services.mappers.ContactMapper;
import io.hypersistence.tsid.TSID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

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
  public List<ContactDTO> findAll(Pageable pageable) {
    List<Contact> contacts = contactRepository.findAll(pageable).toList();
    List<ContactDTO> contactDTOS = contactMapper.toDtolist(contacts);
    return contactDTOS;
  }

  @Override
  public List<ContactDTO> findAllByUser(long id) {
    List<Contact> contacts = contactRepository.findAllByUser(id);
    List<ContactDTO> contactDTOS = contactMapper.toDtolist(contacts);
    return contactDTOS;
  }

  @Override
  public ContactDTO findById(long id) {
    Contact contact =
        contactRepository.findById(id).orElseThrow(() -> new ContactNotFoundException());
    ContactDTO contactDTO = contactMapper.toDto(contact);
    return contactDTO;
  }

  @Override
  public Contact addContact(ContactDTO contactDTO, long idUser) {
    User user = userRepository.findById(idUser).orElseThrow(() -> new UserNotFoundException());

    TSID tsid = TSID.fast();
    Contact contact = contactMapper.toEntity(contactDTO);
    contact.setUser(user);
    contact.setId(tsid.toLong());
    return contactRepository.save(contact);
  }

  @Override
  public String deleteById(long id) {
    findById(id);
    contactRepository.deleteById(id);
    return "Contact has been successfully deleted!";
  }
}
