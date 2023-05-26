package com.ingsoftware.contacts.repositories;

import com.ingsoftware.contacts.models.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

  @Query("SELECT c FROM Contact c WHERE c.user.id=?1")
  List<Contact> findAllByUser(int id);

  @Query("SELECT c FROM Contact c WHERE c.user.id=?1 AND c.id=?2")
  Contact findByIdAndUser(int idUser, int idContact);

  @Query("DELETE FROM Contact c WHERE c.user.id=?1 AND c.id=?2")
  String deleteByUserAndId(int idUser, int idContact);
}
