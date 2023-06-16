package com.ingsoftware.contacts.models.entities;

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@Entity
@Table(name = "users", schema = "public")
public class User implements UserDetails, GrantedAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "tsid")
  @NotNull
  private Long tsid;

  @Column(name = "first_name")
  @Size(max = 25)
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
  @Size(max = 100)
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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> list = new ArrayList<>();
    list.add(new SimpleGrantedAuthority("ROLE_" + this.role));
    return list;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String getAuthority() {
    return role.toString();
  }
}
