package com.ingsoftware.contacts.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "contacts")
public class Contact {

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

  @Column(name = "address")
  @Size(max = 40)
  private String address;

  @Column(name = "phone_number")
  @Size(max = 15)
  @NotNull
  @NotBlank(message = "Phone number is mandatory.")
  private String phoneNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "contact_type")
  @NotNull
  @NotBlank(message = "Contact type is mandatory.")
  private ContactType contactType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @NotNull
  private User user;

  public Contact() {}

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

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public ContactType getContactType() {
    return contactType;
  }

  public void setContactType(ContactType contactType) {
    this.contactType = contactType;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
