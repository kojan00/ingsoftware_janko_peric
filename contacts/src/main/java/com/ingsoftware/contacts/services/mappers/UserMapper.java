package com.ingsoftware.contacts.services.mappers;


import com.ingsoftware.contacts.models.dtos.UserDTO;
import com.ingsoftware.contacts.models.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserDTO toDto(User user);

  List<UserDTO> toDtoList(List<User> users);

  User toEntity(UserDTO userDTO);

  List<User> toEntityList(List<UserDTO> userDTOS);
}
