package com.ingsoftware.contacts.services.implementations;

import com.ingsoftware.contacts.exceptions.ContactTypeNotFoundException;
import com.ingsoftware.contacts.models.dtos.ContactDTO;
import com.ingsoftware.contacts.models.dtos.ContactTypeDTO;
import com.ingsoftware.contacts.models.entities.ContactType;
import com.ingsoftware.contacts.repositories.ContactTypeRepository;
import com.ingsoftware.contacts.services.interfaces.ContactTypeService;
import com.ingsoftware.contacts.services.mappers.ContactTypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ContactTypeServiceImplementation implements ContactTypeService {

  private final ContactTypeRepository contactTypeRepository;

  private final ContactTypeMapper contactTypeMapper;

  public ContactTypeServiceImplementation(
      ContactTypeRepository contactTypeRepository, ContactTypeMapper contactTypeMapper) {
    this.contactTypeRepository = contactTypeRepository;
    this.contactTypeMapper = contactTypeMapper;
  }

  @Override
  public List<ContactTypeDTO> findAll() {
    List<ContactType> contactTypes = contactTypeRepository.findAll();
    List<ContactTypeDTO> contactTypeDTOS = contactTypeMapper.toDtolist(contactTypes);
    return contactTypeDTOS;
  }

  @Override
  public ContactTypeDTO findById(long id) {
    ContactType contactType =
        contactTypeRepository.findById(id).orElseThrow(() -> new ContactTypeNotFoundException());
    ContactTypeDTO contactTypeDTO = contactTypeMapper.toDto(contactType);
    return contactTypeDTO;
  }
}
