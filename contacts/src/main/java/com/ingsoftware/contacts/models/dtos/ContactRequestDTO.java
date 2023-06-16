package com.ingsoftware.contacts.models.dtos;

public record ContactRequestDTO(
    String firstName,
    String lastName,
    String address,
    String phoneNumber,
    ContactTypeDTO contactType,
    UserResponseDTO user) {}
