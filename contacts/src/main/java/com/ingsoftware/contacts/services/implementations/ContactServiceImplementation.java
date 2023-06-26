package com.ingsoftware.contacts.services.implementations;

import com.ingsoftware.contacts.exceptions.ContactNotFoundException;
import com.ingsoftware.contacts.models.dtos.ContactRequestDTO;
import com.ingsoftware.contacts.models.dtos.ContactResponseDTO;
import com.ingsoftware.contacts.models.entities.Contact;
import com.ingsoftware.contacts.models.entities.ContactType;
import com.ingsoftware.contacts.models.entities.User;
import com.ingsoftware.contacts.repositories.ContactRepository;
import com.ingsoftware.contacts.repositories.ContactTypeRepository;
import com.ingsoftware.contacts.repositories.UserRepository;
import com.ingsoftware.contacts.services.interfaces.ContactService;
import com.ingsoftware.contacts.services.mappers.ContactRequestMapper;
import com.ingsoftware.contacts.services.mappers.ContactResponseMapper;
import com.ingsoftware.contacts.services.mappers.ContactTypeMapper;
import io.hypersistence.tsid.TSID;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImplementation implements ContactService {

  private final ContactRepository contactRepository;
  private final UserRepository userRepository;
  private final ContactRequestMapper contactRequestMapper;
  private final ContactResponseMapper contactResponseMapper;

  private final ContactTypeRepository contactTypeRepository;

  private final ContactTypeMapper contactTypeMapper;

  public ContactServiceImplementation(
          ContactRepository contactRepository,
          UserRepository userRepository,
          ContactRequestMapper contactRequestMapper,
          ContactResponseMapper contactResponseMapper, ContactTypeRepository contactTypeRepository, ContactTypeMapper contactTypeMapper) {
    this.contactRepository = contactRepository;
    this.userRepository = userRepository;
    this.contactRequestMapper = contactRequestMapper;
    this.contactResponseMapper = contactResponseMapper;
    this.contactTypeRepository = contactTypeRepository;
    this.contactTypeMapper = contactTypeMapper;
  }

  @Override
  public List<ContactResponseDTO> findAll(Pageable pageable) {
    List<Contact> contacts = contactRepository.findAll(pageable).toList();
    List<ContactResponseDTO> contactResponseDTOS = contactResponseMapper.toDtolist(contacts);
    return contactResponseDTOS;
  }

  @Override
  public List<ContactResponseDTO> findAllByUser(long tsid) {
    List<Contact> contacts = contactRepository.findAllByUser(tsid);
    if (contacts.isEmpty()) {
      throw new ContactNotFoundException();
    }
    List<ContactResponseDTO> contactResponseDTOS = contactResponseMapper.toDtolist(contacts);
    return contactResponseDTOS;
  }

  @Override
  public List<ContactResponseDTO> findAllByFirstNameKeyword(long tsid, String keyword) {
    List<Contact> contacts = contactRepository.findAllByFirstNameKeyword(tsid, keyword);
    List<ContactResponseDTO> contactResponseDTOS = contactResponseMapper.toDtolist(contacts);
    return contactResponseDTOS;
  }

  @Override
  public List<ContactResponseDTO> findAllByLastNameKeyword(long tsid, String keyword) {
    List<Contact> contacts = contactRepository.findAllByLastNameKeyword(tsid, keyword);
    List<ContactResponseDTO> contactResponseDTOS = contactResponseMapper.toDtolist(contacts);
    return contactResponseDTOS;
  }

  @Override
  public List<ContactResponseDTO> findAllByPhoneNumberKeyword(long tsid, String keyword) {
    List<Contact> contacts = contactRepository.findAllByPhoneNumberKeyword(tsid, keyword);
    List<ContactResponseDTO> contactResponseDTOS = contactResponseMapper.toDtolist(contacts);
    return contactResponseDTOS;
  }

  @Override
  public List<ContactResponseDTO> findAllByAddressKeyword(long tsid, String keyword) {
    List<Contact> contacts = contactRepository.findAllByAddressKeyword(tsid, keyword);
    List<ContactResponseDTO> contactResponseDTOS = contactResponseMapper.toDtolist(contacts);
    return contactResponseDTOS;
  }

  @Override
  public ContactResponseDTO save(ContactRequestDTO contactRequestDTO, HttpSession session) {
    Contact contact = contactRequestMapper.toEntity(contactRequestDTO);

    // find who the logged user is and set it for new contact
    long tsidUser = (long) session.getAttribute("tsid");
    User user = userRepository.findByTsid(tsidUser);
    contact.setUser(user);


    // generate tsid for contact
    long tsid = TSID.fast().toLong();
    contact.setTsid(tsid);

    ContactType contactType = contactTypeRepository.findByType(contactRequestDTO.contactType().getType());
    contact.setContactType(contactType);

    contactRepository.save(contact);

    return contactResponseMapper.toDto(contact);
  }

  @Override
  @Transactional
  public String deleteByTsid(long tsid) {
    Contact contactOptional = contactRepository.findByTsid(tsid);

    if (contactOptional == null) {
      throw new ContactNotFoundException();
    }

    contactRepository.deleteByTsid(tsid);
    return "Contact successfully deleted.";
  }
}
