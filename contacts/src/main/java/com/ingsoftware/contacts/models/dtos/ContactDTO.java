package com.ingsoftware.contacts.models.dtos;

import com.ingsoftware.contacts.models.entities.ContactType;

public record ContactDTO(String firstName, String lastName, String address, String phoneNumber, ContactTypeDTO contactType, UserResponseDTO user) {}
