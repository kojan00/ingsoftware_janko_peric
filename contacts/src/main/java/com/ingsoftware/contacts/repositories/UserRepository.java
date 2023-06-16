package com.ingsoftware.contacts.repositories;

import com.ingsoftware.contacts.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  User findByEmail(String email);

  User findByTsid(long tsid);

  String deleteByTsid(long tsid);
}
