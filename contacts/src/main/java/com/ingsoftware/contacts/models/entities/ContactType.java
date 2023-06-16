package com.ingsoftware.contacts.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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

  public ContactType() {}

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
}
