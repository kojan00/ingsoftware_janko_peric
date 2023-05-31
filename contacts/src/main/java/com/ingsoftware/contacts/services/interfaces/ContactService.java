package com.ingsoftware.contacts.services.interfaces;

import com.ingsoftware.contacts.models.dtos.ContactDTO;
import com.ingsoftware.contacts.models.entities.Contact;

import java.util.List;

public interface ContactService {
  List<ContactDTO> findAll();

  List<ContactDTO> findAllByUser(long id);

  ContactDTO findById(long id);

  Contact addContact(ContactDTO contactDTO, long idUser);

  String deleteById(long id);
}
