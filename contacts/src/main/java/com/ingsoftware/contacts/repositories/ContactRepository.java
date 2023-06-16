package com.ingsoftware.contacts.repositories;

import com.ingsoftware.contacts.models.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

  Contact findByTsid(long tsid);

  @Query("SELECT c FROM Contact c WHERE c.user.tsid=?1")
  List<Contact> findAllByUser(long tsid);

  @Query(
      "SELECT c FROM Contact c WHERE c.user.tsid=:tsid AND lower(c.firstName) LIKE lower(concat('%', :keyword,'%'))")
  List<Contact> findAllByFirstNameKeyword(
      @Param("tsid") long tsid, @Param("keyword") String keyword);

  @Query(
      "SELECT c FROM Contact c WHERE c.user.tsid=:tsid AND lower(c.lastName) LIKE lower(concat('%', :keyword,'%'))")
  List<Contact> findAllByLastNameKeyword(
      @Param("tsid") long tsid, @Param("keyword") String keyword);

  @Query(
      "SELECT c FROM Contact c WHERE c.user.tsid=:tsid AND lower(c.address) LIKE lower(concat('%', :keyword,'%'))")
  List<Contact> findAllByAddressKeyword(@Param("tsid") long tsid, @Param("keyword") String keyword);

  @Query(
      "SELECT c FROM Contact c WHERE c.user.tsid=:tsid AND lower(c.phoneNumber) LIKE lower(concat('%', :keyword,'%'))")
  List<Contact> findAllByPhoneNumberKeyword(
      @Param("tsid") long id, @Param("keyword") String keyword);

  String deleteByTsid(long tsid);
}
