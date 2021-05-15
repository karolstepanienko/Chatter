package com.chatter.controllers.AccountController;

import com.chatter.model.Constants.AccountPrivacies;
import com.chatter.model.Constants.Roles;
import com.chatter.model.User.User;
import com.chatter.model.User.UserDTO;
import com.chatter.repositories.UserRepository;

public final class AccountControllerLogic {

  /**
   * Checks against the database if provided userName is available.
   * @param user Given user with provided userName.
   * @param userRepository User repository providing access
   * to database with user data.
   * @return True if userName is available. False otherwise.
   */
  public boolean checkUserNameAvailable(final User user,
   final UserRepository userRepository) {
    return userRepository.getUserWithUserName(user.getUserName()) == null;
  }

  /**
   * Checks against the database if provided email is available.
   * @param user Given user with provided email.
   * @param userRepository User repository providing access
   * to database with user data.
   * @return True if email is available. False otherwise.
   */
  public boolean checkUserEmailAvailable(final User user,
  final UserRepository userRepository) {
    return userRepository.getUserWithEmail(user.getEmail()) == null;
  }

  /**
   * Checks if provided user has unique userName and email.
   * @param user Provided user data.
   * @param userRepository User repository providing access
   * to database with user data.
   * @return True if user is unique. False otherwise.
   */
  public boolean checkUserUnique(
    final User user,
    final UserRepository userRepository) {
    return checkUserNameAvailable(user, userRepository)
      && checkUserEmailAvailable(user, userRepository);
  }

  /**
   * Creates user object from user data transfer object.
   * @param userDTO Provided user DTO object.
   * @param passwordHash Passwrd hashed by BCrypt.
   * @return Created user object.
   */
  public User createUserFromUserDTO(
    final UserDTO userDTO,
    final String passwordHash) {
    User user = new User();
    user.setUserName(userDTO.getUserName());
    user.setEmail(userDTO.getEmail());
    user.setPasswordHash(passwordHash);
    user.setRole(Roles.getUserRole());
    user.setAccountPrivacy(AccountPrivacies.getPrivateAccess());
    return user;
  }
}
