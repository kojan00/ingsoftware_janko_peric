package com.ingsoftware.contacts.exceptions;

public class ContactNotFoundException extends RuntimeException {

  public ContactNotFoundException() {
    super("Contact not found.");
  }
}
