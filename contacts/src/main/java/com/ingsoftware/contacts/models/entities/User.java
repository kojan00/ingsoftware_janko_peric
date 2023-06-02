package com.ingsoftware.contacts.models.entities;

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Table(name = "users", schema = "public")
public class User {

  @Id private Long id;

  @Column(name = "first_name")
  @Size(max = 25)
  @NotNull
  @NotBlank(message = "Name is mandatory.")
  private String firstName;

  @Column(name = "last_name")
  @Size(max = 25)
  @NotNull
  @NotBlank(message = "Surname is mandatory.")
  private String lastName;

  @Column(name = "email")
  @Size(max = 25)
  @NotNull
  @NotBlank(message = "Email is mandatory.")
  private String email;

  @Column(name = "password")
  @Size(max = 25)
  @NotNull
  @NotBlank(message = "Password is mandatory.")
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "role")
  @Type(PostgreSQLEnumType.class)
  @NotNull
  private Role role;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
  private List<Contact> contacts;

  public User() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public List<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(List<Contact> contacts) {
    this.contacts = contacts;
  }
}
