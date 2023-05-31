package com.ingsoftware.contacts.models.dtos;

import com.ingsoftware.contacts.models.entities.Role;

public record UserResponseDTO(String firstName, String lastName, String email, Role role) {}
