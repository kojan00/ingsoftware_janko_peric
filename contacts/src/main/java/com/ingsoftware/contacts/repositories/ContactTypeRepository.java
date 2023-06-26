package com.ingsoftware.contacts.repositories;

import com.ingsoftware.contacts.models.entities.ContactType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactTypeRepository extends JpaRepository<ContactType, Integer> {
  ContactType findByTsid(long tsid);

  ContactType findByType(String type);

  @Modifying
  @Transactional
  @Query("UPDATE ContactType ct SET ct.type = :type WHERE ct.tsid = :tsid")
  void updateContactType(@Param("type") String type, @Param("tsid") long tsid);
}
