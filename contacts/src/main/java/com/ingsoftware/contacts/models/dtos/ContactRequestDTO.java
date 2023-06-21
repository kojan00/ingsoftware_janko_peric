package com.ingsoftware.contacts.models.dtos;

import com.ingsoftware.contacts.models.entities.ContactType;

public record ContactRequestDTO(
    String firstName,
    String lastName,
    String address,
    String phoneNumber,
    ContactType contactType) {}
