package com.ingsoftware.contacts.exceptions;

public class ContactTypeNotFoundException extends RuntimeException {

  public ContactTypeNotFoundException() {
    super("Contact type not found.");
  }
}
