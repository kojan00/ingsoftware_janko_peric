package com.ingsoftware.contacts.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "contact_type")
public class ContactType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "tsid")
  private Long tsid;

  @Column(name = "type")
  @NotNull
  @NotBlank
  @Size(max = 25)
  private String type;

  @OneToMany(mappedBy = "contactType", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Contact> contacts;

  public ContactType() {}

  public ContactType(Long tsid, @NotNull String type) {
    this.tsid = tsid;
    this.type = type;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Long getTsid() {
    return tsid;
  }

  public void setTsid(Long tsid) {
    this.tsid = tsid;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(List<Contact> contacts) {
    this.contacts = contacts;
  }
  
}
