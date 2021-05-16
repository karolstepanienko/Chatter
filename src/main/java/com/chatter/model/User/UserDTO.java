package com.chatter.model.User;

import lombok.Data;

/**
* User Data Transfer Object class.
* Only difference from User is that
* it has password in plain text.
 */
@Data
public class UserDTO {

  /**
   * User ID. Used to identify user in database.
   * Generated automatically.
   */
  private Integer id;

  /**
   * User userName. Unique amongst users,
   */
  private String userName;

  /**
   * User login not checked.
   */
  private String login;

  /**
   * User email.
   */
  private String email;

  /**
   * User password in plain text.
   */
  private String password;

  /**
   * User account role.
   */
  private String role;

  /**
   * Basic constructor.
   */
  public UserDTO() { }

  /**
   * Constructor.
   * @param id User ID.
   * @param userName User userName.
   * @param login User login.
   * @param email User email.
   * @param password User password.
   * @param role User role.
   */
  public UserDTO(
    final Integer id,
    final String userName,
    final String login,
    final String email,
    final String password,
    final String role) {
    this.id = id;
    this.userName = userName;
    this.login = login;
    this.email = email;
    this.password = password;
    this.role = role;
  }
}
