package com.chatter.model.User;

import lombok.Data;

@Data
public class UserINFO {

  private Integer id;
  private String userName;
  private String login;
  private String email;
  private String accountPrivacy;
  private String role;

  public UserINFO() {}

  public UserINFO(UserDTO userDTO) {
    this.id = userDTO.getId();
    this.userName = userDTO.getUserName();
    this.login = userDTO.getLogin();
    this.email = userDTO.getEmail();
    this.role = userDTO.getRole();
    this.accountPrivacy = "";
  }

  public UserINFO(User user) {
    this.id = user.getId();
    this.userName = user.getUserName();
    this.login = user.getLogin();
    this.email = user.getEmail();
    this.role = user.getRole();
    this.accountPrivacy = user.getAccountPrivacy();
  } 
}
