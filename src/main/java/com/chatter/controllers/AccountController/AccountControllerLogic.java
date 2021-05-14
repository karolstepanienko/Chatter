package com.chatter.controllers.AccountController;


import com.chatter.model.Constants.AccountPrivacies;
import com.chatter.model.Constants.Roles;
import com.chatter.model.User.User;
import com.chatter.model.User.UserDTO;
import com.chatter.repositories.UserRepository;


public class AccountControllerLogic {

  public void deleteUser(User user, UserRepository userRepository) {
    user.setId(userRepository.getUserWithUserName(user.getUserName()).getId());
    userRepository.delete(user);
  }

  public boolean checkUserNameAvailable(User user, UserRepository userRepository) {
    // System.out.println(userRepository.getUserWithLogin(user.getLogin()).toString());
    if (userRepository.getUserWithUserName(user.getUserName()) == null) return true;
    else return false;
  }

  public boolean checkUserEmailAvailable(User user, UserRepository userRepository) {
    // System.out.println(userRepository.getUserWithEmail(user.getEmail()).toString());
    if (userRepository.getUserWithEmail(user.getEmail()) == null) return true;
    else return false;
  }

  public boolean checkUserUnique(User user, UserRepository userRepository) {
    if (checkUserNameAvailable(user, userRepository) &&
      checkUserEmailAvailable(user, userRepository) ) return true;
    else return false;
  }

  public User createUserFromUserDTO(UserDTO userDTO, String passwordHash) {
    User user = new User();
    user.setUserName(userDTO.getUserName());
    user.setEmail(userDTO.getEmail());
    user.setPasswordHash(passwordHash);
    user.setRole(Roles.getUserRole());
    user.setAccountPrivacy(AccountPrivacies.getPrivateAccess());
    return user;

  }
}
