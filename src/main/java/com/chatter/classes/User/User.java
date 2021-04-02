package com.chatter.classes.User;


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
  private String name;
  private String login;
  private String email;

  public User() {}

  public User(String name, String login, String email) {
    this.name = name;
    this.login = login;
    this.email = email;
  }

  // Getters
  public Integer getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getLogin() {
    return this.login;
  }

  public String getEmail() {
    return this.email;
  }

  // Setters
  public void setId(Integer id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setLogin(String login) {
    this.login= login;
  }


  public void setEmail(String email) {
    this.email = email;
  }

  public String toString() {
    return "Id: " + this.id + ", " +
    "Name: " + this.name + ", " +
    "Login: " + this.login + ", " +
    "Email: " + this.email;
  }
  
}
