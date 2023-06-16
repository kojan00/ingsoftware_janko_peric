package com.ingsoftware.contacts.services.mappers;

import com.ingsoftware.contacts.models.dtos.ContactTypeDTO;
import com.ingsoftware.contacts.models.entities.ContactType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactTypeMapper {
  ContactTypeDTO toDto(ContactType contactType);

  List<ContactTypeDTO> toDtolist(List<ContactType> contactTypes);

  ContactType toEntity(ContactTypeDTO contactTypeDTO);

  List<ContactType> toEntityList(List<ContactTypeDTO> contactTypeDTOS);
}
