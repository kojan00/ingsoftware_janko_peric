package com.ingsoftware.contacts.services.mappers;

import com.ingsoftware.contacts.models.dtos.ContactRequestDTO;
import com.ingsoftware.contacts.models.dtos.ContactResponseDTO;
import com.ingsoftware.contacts.models.entities.Contact;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactResponseMapper {

  ContactResponseDTO toDto(Contact contact);

  List<ContactResponseDTO> toDtolist(List<Contact> contacts);

  Contact toEntity(ContactResponseDTO contactResponseDTO);

  List<Contact> toEntityList(List<ContactResponseDTO> contactResponseDTOS);
}
