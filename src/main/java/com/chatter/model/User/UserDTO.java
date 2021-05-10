package com.chatter.model.User;

// Class DTO - User Data Transfer Object
// Only difference from User is that
// it has password in plain text

public class UserDTO {

  private Integer id;
  private String userName;
  private String login;
  private String email;
  private String password;
  private String role;

  public UserDTO() {}

  public UserDTO(Integer id, String userName, String login, String email, String password, String role) {
    this.id = id;
    this.userName = userName;
    this.login = login;
    this.email = email;
    this.password = password;
    this.role = role;
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

  public String getPassword() {
    return this.password;
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

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String toString() {
    return "Id: " + this.id + ", " +
    "Username: " + this.userName + ", " +
    "Login: " + this.login + ", " +
    "Email: " + this.email + ", " + 
    "Password: " + this.password + ", " +
    "Role: " + this.role;
  }
  
}
