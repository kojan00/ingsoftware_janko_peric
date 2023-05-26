package com.ingsoftware.contacts.services.interfaces;

import com.ingsoftware.contacts.models.dtos.UserDTO;
import com.ingsoftware.contacts.models.entities.User;

import java.util.List;

public interface UserService {

  List<UserDTO> findAll();

  UserDTO findById(int id);

  User save(User user);

  String deleteById(int id);
}
