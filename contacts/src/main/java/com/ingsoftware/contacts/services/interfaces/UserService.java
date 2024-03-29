package com.ingsoftware.contacts.services.interfaces;

import com.ingsoftware.contacts.models.dtos.UserResponseDTO;
import com.ingsoftware.contacts.models.dtos.UserRegistrationDTO;
import com.ingsoftware.contacts.models.entities.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

  List<UserResponseDTO> findAll();

  List<UserResponseDTO> findAllPaginated(Pageable pageable);

  UserResponseDTO findByTsid(long tsid);

  String verifyEmail(String email);

  UserResponseDTO save(UserRegistrationDTO userRegistrationDTO);

  String deleteByTsid(long tsid);
}
