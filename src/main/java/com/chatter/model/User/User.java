package com.chatter.model.User;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// This tells Hibernate to make a table in database out of this class
// Hibernate automatically translates the entity into a table.
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String userName;
  private String login;
  private String email;
  private String passwordHash;
  private String role;

  public User() {}

  public User(String userName, String login, String email, String passwordHash) {
    this.userName = userName;
    this.login = login;
    this.email = email;
    this.passwordHash = passwordHash;
  }

  // Getters
  public Integer getId() {
    return this.id;
  }

  public String getUserName() {
    return this.userName;
  }

  public String getLogin() {
    return this.login;
  }

  public String getEmail() {
    return this.email;
  }

  public String getPasswordHash() {
    return this.passwordHash;
  }

  public String getRole() {
    return this.role;
  }


  // Setters
  public void setId(Integer id) {
    this.id = id;
  }

  public void setName(String userName) {
    this.userName = userName;
  }

  public void setLogin(String login) {
    this.login= login;
  }


  public void setEmail(String email) {
    this.email = email;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String toString() {
    return "Id: " + this.id + ", " +
    "Username: " + this.userName + ", " +
    "Login: " + this.login + ", " +
    "Email: " + this.email + ", " + 
    "PasswordHash: " + this.passwordHash + ", " +
    "Role: " + this.role;
  }
  
}
