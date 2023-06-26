package com.ingsoftware.contacts.services.interfaces;

import com.ingsoftware.contacts.models.dtos.ContactTypeDTO;
import com.ingsoftware.contacts.models.entities.ContactType;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactTypeService {

  List<ContactTypeDTO> findAll();

  ContactTypeDTO findByTsid(long tsid);


  ContactType save(ContactTypeDTO contactTypeDTO);

  void updateContactType(String type, long tsid);
}
