package com.ingsoftware.contacts.services.implementations;

import com.ingsoftware.contacts.exceptions.ContactTypeNotFoundException;

import com.ingsoftware.contacts.models.dtos.ContactTypeDTO;
import com.ingsoftware.contacts.models.entities.ContactType;
import com.ingsoftware.contacts.repositories.ContactTypeRepository;
import com.ingsoftware.contacts.services.interfaces.ContactTypeService;
import com.ingsoftware.contacts.services.mappers.ContactTypeMapper;
import io.hypersistence.tsid.TSID;
import org.springframework.stereotype.Service;

import java.util.List;

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
  public ContactTypeDTO findByTsid(long tsid) {
    ContactType contactType = contactTypeRepository.findByTsid(tsid);

    if (contactType == null) {
      throw new ContactTypeNotFoundException();
    }
    ContactTypeDTO contactTypeDTO = contactTypeMapper.toDto(contactType);
    return contactTypeDTO;
  }

  @Override
  public ContactType save(ContactTypeDTO contactTypeDTO) {
    ContactType contactType = contactTypeMapper.toEntity(contactTypeDTO);
    long tsid = TSID.fast().toLong();
    contactType.setTsid(tsid);
    return contactTypeRepository.save(contactType);
  }

  @Override
  public void updateContactType(String type, long tsid) {
    ContactType contactType = contactTypeRepository.findByTsid(tsid);
    contactTypeRepository.updateContactType(type, tsid);
  }
}
