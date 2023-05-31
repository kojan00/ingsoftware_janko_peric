package com.ingsoftware.contacts.services.mappers;

import com.ingsoftware.contacts.models.dtos.ContactDTO;
import com.ingsoftware.contacts.models.entities.Contact;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactMapper {
  ContactDTO toDto(Contact contact);

  List<ContactDTO> toDtolist(List<Contact> contacts);

  Contact toEntity(ContactDTO contactDTO);

  List<Contact> toEntityList(List<ContactDTO> contactDTOS);
}
