package com.ingsoftware.contacts.models.dtos;

import com.ingsoftware.contacts.models.entities.Role;

public record UserResponseDTO(
    long tsid, String firstName, String lastName, String email, Role role, String phoneNumber) {}
