package com.ingsoftware.contacts.models.dtos;

public record ContactResponseDTO(String firstName, String lastName, String address, String phoneNumber, ContactTypeDTO contactType) {}
