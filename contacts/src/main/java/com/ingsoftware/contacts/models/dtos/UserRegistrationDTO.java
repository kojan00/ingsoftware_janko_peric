package com.ingsoftware.contacts.models.dtos;

import com.ingsoftware.contacts.models.entities.Role;

public record UserRegistrationDTO(String firstName, String lastName, String email, String password, Role role, String phoneNumber) {}
