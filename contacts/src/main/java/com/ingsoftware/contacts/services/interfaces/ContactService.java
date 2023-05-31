package com.ingsoftware.contacts.services.interfaces;

import com.ingsoftware.contacts.models.dtos.ContactDTO;
import com.ingsoftware.contacts.models.entities.Contact;

import java.util.List;

public interface ContactService {
  List<ContactDTO> findAll();

  List<ContactDTO> findAllByUser(int id);

  ContactDTO findById(int id);

  Contact addContact(Contact contact, int idUser);

  String deleteById(int id);
}
