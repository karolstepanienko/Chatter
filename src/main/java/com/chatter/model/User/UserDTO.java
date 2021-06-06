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
   * User account roles.
   */
	private String role;

  /**
   * Basic constructor.
   */
  public UserDTO() { }

  /**
   * Constructor.
   * @param newId User ID.
   * @param newUserName User userName.
   * @param newLogin User login.
   * @param newEmail User email.
   * @param newPassword User password.
   * @param newRole User role.
   */
  public UserDTO(
    final Integer newId,
    final String newUserName,
    final String newLogin,
    final String newEmail,
    final String newPassword,
    final String newRole) {
    this.id = newId;
    this.userName = newUserName;
    this.login = newLogin;
    this.email = newEmail;
    this.password = newPassword;
    this.role = newRole;
  }
}
