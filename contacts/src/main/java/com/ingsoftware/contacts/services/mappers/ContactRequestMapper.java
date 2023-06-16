package com.ingsoftware.contacts.services.mappers;

import com.ingsoftware.contacts.models.dtos.ContactRequestDTO;
import com.ingsoftware.contacts.models.entities.Contact;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactRequestMapper {
  ContactRequestDTO toDto(Contact contact);

  List<ContactRequestDTO> toDtolist(List<Contact> contacts);

  Contact toEntity(ContactRequestDTO contactRequestDTO);

  List<Contact> toEntityList(List<ContactRequestDTO> contactRequestDTOS);
}
