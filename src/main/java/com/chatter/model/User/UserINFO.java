package com.chatter.model.User;

import lombok.Data;

/**
 * Sends data for site to display.
 */
@Data
public class UserINFO {

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
   * User privacy setting.
   */
  private String accountPrivacy;

  /**
   * User account role.
   */
  private String role;

  /**
   * Constructor.
   */
  public UserINFO() { }

  /**
   * Constructor.
   * @param userDTO Given user data.
   */
  public UserINFO(final UserDTO userDTO) {
    this.id = userDTO.getId();
    this.userName = userDTO.getUserName();
    this.login = userDTO.getLogin();
    this.email = userDTO.getEmail();
    this.role = userDTO.getRole();
    this.accountPrivacy = "";
  }

  /**
   * Constructor.
   * @param user Given user data.
   */
  public UserINFO(final User user) {
    this.id = user.getId();
    this.userName = user.getUserName();
    this.login = user.getLogin();
    this.email = user.getEmail();
    this.role = user.getRole();
    this.accountPrivacy = user.getAccountPrivacy();
  }
}
