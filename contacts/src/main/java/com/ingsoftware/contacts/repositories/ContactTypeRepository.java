package com.ingsoftware.contacts.repositories;

import com.ingsoftware.contacts.models.entities.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactTypeRepository extends JpaRepository<ContactType, Long> {}
