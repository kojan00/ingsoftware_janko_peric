package com.ingsoftware.contacts.services.interfaces;

import com.ingsoftware.contacts.models.dtos.UserResponseDTO;
import com.ingsoftware.contacts.models.dtos.UserRegistrationDTO;
import com.ingsoftware.contacts.models.entities.User;

import java.util.List;

public interface UserService {

  List<UserResponseDTO> findAll();

  UserResponseDTO findById(long id);

  User save(UserRegistrationDTO userRegistrationDTO);

  String deleteById(long id);
}
