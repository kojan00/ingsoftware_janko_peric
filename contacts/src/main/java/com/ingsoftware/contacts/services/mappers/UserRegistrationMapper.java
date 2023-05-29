package com.ingsoftware.contacts.services.mappers;

import com.ingsoftware.contacts.models.dtos.UserRegistrationDTO;
import com.ingsoftware.contacts.models.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserRegistrationMapper {
  UserRegistrationDTO toDto(User user);

  List<UserRegistrationDTO> toDtoList(List<User> users);

  User toEntity(UserRegistrationDTO userRegistrationDTO);

  List<User> toEntityList(List<UserRegistrationDTO> userRegistrationDTOS);
}
