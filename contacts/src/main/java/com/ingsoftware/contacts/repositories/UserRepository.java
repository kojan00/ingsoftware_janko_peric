package com.ingsoftware.contacts.repositories;

import com.ingsoftware.contacts.models.entities.Role;
import com.ingsoftware.contacts.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  List<User> findAllByRole(Role role);
  User findByEmail(String email);

  User findByTsid(long tsid);

  String deleteByTsid(long tsid);
}
