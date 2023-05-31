package com.ingsoftware.contacts.services.mappers;


import com.ingsoftware.contacts.models.dtos.UserResponseDTO;
import com.ingsoftware.contacts.models.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserResponseDTO toDto(User user);

  List<UserResponseDTO> toDtoList(List<User> users);

  User toEntity(UserResponseDTO userResponseDTO);

  List<User> toEntityList(List<UserResponseDTO> userResponseDTOS);
}
