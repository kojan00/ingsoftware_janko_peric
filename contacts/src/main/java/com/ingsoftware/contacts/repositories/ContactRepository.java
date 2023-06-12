package com.ingsoftware.contacts.repositories;

import com.ingsoftware.contacts.models.dtos.ContactDTO;
import com.ingsoftware.contacts.models.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

  @Query("SELECT c FROM Contact c WHERE c.user.id=?1")
  List<Contact> findAllByUser(long id);

  @Query("SELECT c FROM Contact c WHERE c.user.id=:id AND lower(c.firstName) LIKE lower(concat('%', :keyword,'%'))")
  List<Contact> findAllByFirstNameKeyword(@Param("id") long id, @Param("keyword") String keyword);

  @Query("SELECT c FROM Contact c WHERE c.user.id=:id AND lower(c.lastName) LIKE lower(concat('%', :keyword,'%'))")
  List<Contact> findAllByLastNameKeyword(@Param("id") long id, @Param("keyword") String keyword);

  @Query("SELECT c FROM Contact c WHERE c.user.id=:id AND lower(c.address) LIKE lower(concat('%', :keyword,'%'))")
  List<Contact> findAllByAddressKeyword(@Param("id") long id, @Param("keyword") String keyword);

  @Query("SELECT c FROM Contact c WHERE c.user.id=:id AND lower(c.phoneNumber) LIKE lower(concat('%', :keyword,'%'))")
  List<Contact> findAllByPhoneNumberKeyword(@Param("id") long id, @Param("keyword") String keyword);





}
