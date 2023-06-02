package com.ingsoftware.contacts.services.interfaces;

import com.ingsoftware.contacts.models.dtos.ContactDTO;
import com.ingsoftware.contacts.models.dtos.ContactTypeDTO;

import java.util.List;

public interface ContactTypeService {

  List<ContactTypeDTO> findAll();

  ContactTypeDTO findById(long id);
}
