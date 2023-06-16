package com.ingsoftware.contacts.services.interfaces;

import com.ingsoftware.contacts.models.dtos.ContactRequestDTO;
import com.ingsoftware.contacts.models.dtos.ContactResponseDTO;
import com.ingsoftware.contacts.models.entities.Contact;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactService {

  List<ContactResponseDTO> findAll(Pageable pageable);

  List<ContactResponseDTO> findAllByUser(long tsid);

  List<ContactResponseDTO> findAllByAddressKeyword(long tsid, String keyword);

  List<ContactResponseDTO> findAllByFirstNameKeyword(long tsid, String keyword);

  List<ContactResponseDTO> findAllByLastNameKeyword(long tsid, String keyword);

  List<ContactResponseDTO> findAllByPhoneNumberKeyword(long tsid, String keyword);

  Contact save(ContactRequestDTO contactRequestDTO);

  String deleteByTsid(long tsid);
}
