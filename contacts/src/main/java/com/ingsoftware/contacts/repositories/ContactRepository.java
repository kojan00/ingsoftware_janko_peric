package com.ingsoftware.contacts.repositories;

import com.ingsoftware.contacts.models.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

  @Query("SELECT c FROM Contact c WHERE c.user.id=?1")
  List<Contact> findAllByUser(long id);

  @Query("SELECT c FROM Contact c WHERE c.user.id=?1 AND c.id=?2")
  Contact findByIdAndUser(long idUser, long idContact);

  @Query("DELETE FROM Contact c WHERE c.user.id=?1 AND c.id=?2")
  String deleteByUserAndId(long idUser, long idContact);
}
