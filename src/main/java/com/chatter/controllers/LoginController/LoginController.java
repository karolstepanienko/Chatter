package com.chatter.controllers.LoginController;

// Project imports
import com.chatter.model.User.User;
import com.chatter.model.User.UserDTO;
import com.chatter.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account/login")
public class LoginController {

  /**
   * User repository used to communicate with database.
   * Makes user related changes.
   * */
  @Autowired
  private UserRepository userRepository;

  /**
   * Password encoder used to create password
   * hashes and compare user submited passwords
   * to hashes stored in database.
   * */
  @Autowired
  private PasswordEncoder passwordEncoder;

  /**
   * @HTTPRequestMethod POST
   * Verifies the given user data against the database.
   * @param unverifiedUser Provided unverified user data.
   * @return Verified user if provided data was correct. False otherwise.
   */
  @CrossOrigin
  @PostMapping("/user/get")
  public @ResponseBody User getVerifiedUser(
    @RequestBody final User unverifiedUser) {
    User verifiedUser = this.userRepository
      .getUserWithUserName(unverifiedUser.getUserName());
    if (verifiedUser != null) {
      System.out.println(verifiedUser);
      return verifiedUser;
    } else {
      return null;
    }
  }

  /**
   * @HTTPRequestMethod POST
   * Checks user password against the data in database.
   * @param userDTO Provided user data.
   * @return True if provided data was correct. False otherwise.
   */
  @CrossOrigin
  @PostMapping("/user/check/password")
  public @ResponseBody boolean checkUserPassword(
    @RequestBody final UserDTO userDTO) {
    User verifiedUser = this.userRepository
      .getUserWithUserName(userDTO.getUserName());
    if (verifiedUser != null) {
      return this.passwordEncoder.matches(
        userDTO.getPassword(),
        verifiedUser.getPasswordHash());
    } else {
      return false;
    }
  }
}
