package com.ingsoftware.contacts.services.interfaces;

import com.ingsoftware.contacts.models.dtos.ContactDTO;
import com.ingsoftware.contacts.models.entities.Contact;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactService {

  List<ContactDTO> findAll(Pageable pageable);

  List<ContactDTO> findAllByUser(long id);

  List<ContactDTO> findAllByAddressKeyword(long id, String keyword);

  List<ContactDTO> findAllByFirstNameKeyword(long id, String keyword);

  List<ContactDTO> findAllByLastNameKeyword(long id, String keyword);

  List<ContactDTO> findAllByPhoneNumberKeyword(long id, String keyword);

  ContactDTO findById(long id);

  Contact addContact(ContactDTO contactDTO, long idUser);

  String deleteById(long id);
}
