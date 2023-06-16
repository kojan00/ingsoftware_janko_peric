package com.ingsoftware.contacts.services.implementations;

import com.ingsoftware.contacts.exceptions.ContactNotFoundException;
import com.ingsoftware.contacts.models.dtos.ContactRequestDTO;
import com.ingsoftware.contacts.models.dtos.ContactResponseDTO;
import com.ingsoftware.contacts.models.entities.Contact;
import com.ingsoftware.contacts.repositories.ContactRepository;
import com.ingsoftware.contacts.repositories.UserRepository;
import com.ingsoftware.contacts.services.interfaces.ContactService;
import com.ingsoftware.contacts.services.mappers.ContactRequestMapper;
import com.ingsoftware.contacts.services.mappers.ContactResponseMapper;
import io.hypersistence.tsid.TSID;
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

  public ContactServiceImplementation(
      ContactRepository contactRepository,
      UserRepository userRepository,
      ContactRequestMapper contactRequestMapper,
      ContactResponseMapper contactResponseMapper) {
    this.contactRepository = contactRepository;
    this.userRepository = userRepository;
    this.contactRequestMapper = contactRequestMapper;
    this.contactResponseMapper = contactResponseMapper;
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
  public Contact save(ContactRequestDTO contactRequestDTO) {
    Contact contact = contactRequestMapper.toEntity(contactRequestDTO);

    long tsid = TSID.fast().toLong();
    contact.setTsid(tsid);

    return contactRepository.save(contact);
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
