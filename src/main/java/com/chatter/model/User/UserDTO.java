package com.chatter.model.User;

import lombok.Data;

// Class DTO - User Data Transfer Object
// Only difference from User is that
// it has password in plain text

@Data
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
}
