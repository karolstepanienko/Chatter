package com.chatter.controllers.UserController;

import com.chatter.model.Constants.AccountPrivacies;
// Project imports
import com.chatter.model.User.User;
import com.chatter.model.User.UserDTO;
import com.chatter.model.User.UserINFO;
import com.chatter.repositories.PostRepository;
import com.chatter.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account/user")
public class UserController {

  /**
   * User repository used to communicate with database.
   * Makes user related changes.
   * */
  @Autowired
  private UserRepository userRepository;

  /**
   * Post repository used to communicate with database.
   * Makes post related changes.
   */
  @Autowired
  private PostRepository postRepository;

  /**
   * Password encoder used to create password
   * hashes and compare user submited passwords
   * to hashes stored in database.
   * */
  @Autowired
  private PasswordEncoder passwordEncoder;

  /**
   * @HTTPRequestMethod
   * @Tested
   * Checks against the database if provided userName is available.
   * @param userName Provided userName.
   * @return True if userName is available. False otherwise.
   */
  @CrossOrigin
  @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
  @GetMapping("/check/username")
  public boolean checkUserNameUnique(@RequestParam final String userName) {
    return this.userRepository.getUserWithUserName(userName) == null;
  }

  /**
   * @HTTPRequestMethod GET
   * @Tested
   * Checks against the database if provided email is available.
   * @param email Provided email.
   * @return True if email is available. False otherwise.
   */
  @CrossOrigin
  @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
  @GetMapping("/check/email")
  public boolean checkEmailUnique(@RequestParam final String email) {
    return this.userRepository.getUserWithEmail(email) == null;
  }

  /**
   * @HTTPRequestMethod GET
   * @Tested
   * Verifies credentials provided on login.
   * @param userName Provided userName.
   * @param password Provided plain text password.
   * @return Verified user if provided data was correct. False otherwise.
   */
  @CrossOrigin
  @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
  @GetMapping("/get")
  public @ResponseBody User getVerifiedUser(
    @RequestParam final String userName,
    @RequestParam final String password) {
    User user = this.userRepository.getUserWithUserName(userName);
    if (user != null
    && this.passwordEncoder.matches(password, user.getPasswordHash())) {
      return user;
    } else {
      return null;
    }
  }

  /**
   * @HTTPRequestMethod GET
   * @Tested
   * @param userName Provided userName.
   * @return User object with provided userName. Null if user does not exist.
   */
  @CrossOrigin
  @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
  @GetMapping("/get/user/by/userName")
  public @ResponseBody User getUserWithUserName(
    @RequestParam final String userName) {
    User user = this.userRepository.getUserWithUserName(userName);
    return user;
  }

  /**
   * @HTTPRequestMethod
   * @Tested
   * @param userName Provided userName.
   * @return User's ID whose userName was provided.
   */
  @CrossOrigin
  @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
  @GetMapping("/get/id/by/userName")
  public @ResponseBody Integer getUserId(@RequestParam final String userName) {
    User user = this.userRepository.getUserWithUserName(userName);
    if (user != null) {
      return user.getId();
    } else {
      return null;
    }
  }

  /**
   * Upadates user login.
   * @Tested
   * @param userDTO Provided user data with new login.
   * @return True if provided upadate was successfull. False otherwise.
   */
  @CrossOrigin
  @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
  @PostMapping("/update/login")
  public boolean updateUserLogin(@RequestBody final UserDTO userDTO) {
    User user = this.userRepository.getUserWithId(userDTO.getId());
    if (user != null) {
      user.setLogin(userDTO.getLogin());
      this.userRepository.save(user);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Upadates user email. Checks if email is unique.
   * @Tested
   * @param userDTO Provided user data with new email.
   * @return True if provided upadate was successfull. False otherwise.
   */
  @CrossOrigin
  @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
  @PostMapping("/update/email")
  public boolean updateUserEmail(@RequestBody final UserDTO userDTO) {
    User user = this.userRepository.getUserWithId(userDTO.getId());
    user.setEmail(userDTO.getEmail());
    if (user != null && !this.userRepository.existsByEmail(user.getEmail())) {
      this.userRepository.save(user);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Upadates user account privacy.
   * @Tested
   * @param userINFO Provided user data with new privacy setting.
   * @return True if provided upadate was successfull. False otherwise.
   */
  @CrossOrigin
  @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
  @PostMapping("/update/privacy")
  public boolean updateUserPrivacy(@RequestBody final UserINFO userINFO) {
    User user = this.userRepository.getUserWithId(userINFO.getId());
    if (user != null
      && AccountPrivacies.getAllList().contains(userINFO.getAccountPrivacy())) {
        user.setAccountPrivacy(userINFO.getAccountPrivacy());
        this.userRepository.save(user);
        return true;
    } else {
      return false;
    }
  }

  /**
   * Delete user account, all his posts and likes.
   * @param userId ID of a user who will be deleted.
   * @return True if delete operation was successfull. False otherwise.
   */
  @CrossOrigin
  @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
  @PostMapping("/delete")
  public boolean deleteUserAndAllHisPosts(@RequestParam final Integer userId) {
    User user = this.userRepository.getUserWithId(userId);
    if (user != null) {
      this.postRepository.deleteUserLikes(userId);
      this.postRepository.deleteUserPosts(userId);
      this.userRepository.delete(user);
      return true;
    } else {
      return false;
    }
  }
}
