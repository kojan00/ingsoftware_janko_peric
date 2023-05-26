package com.ingsoftware.contacts.models.dtos;

import com.ingsoftware.contacts.models.entities.ContactType;

public class ContactDTO {

  private String firstName;

  private String lastName;

  private String address;

  private String phoneNumber;

  private ContactType contactType;

  private UserDTO user;

  public ContactDTO() {}

  public ContactDTO(
      String firstName,
      String lastName,
      String address,
      String phoneNumber,
      ContactType contactType,
      UserDTO user) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.contactType = contactType;
    this.user = user;
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

  public UserDTO getUserDTO() {
    return user;
  }

  public void setUserDTO(UserDTO user) {
    this.user = user;
  }
}
