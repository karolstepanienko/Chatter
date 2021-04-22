package com.chatter.controllers.AccountController;


import com.chatter.model.User.User;
import com.chatter.repositories.UserRepository;


public class AccountControllerLogic {
  public void deleteUser(User user, UserRepository userRepository) {
    user.setId(userRepository.getUserWithName(user.getName()).getId());
    userRepository.delete(user);
  }

  public boolean checkUserLoginAvailable(User user, UserRepository userRepository) {
    // System.out.println(userRepository.getUserWithLogin(user.getLogin()).toString());
    if (userRepository.getUserWithLogin(user.getLogin()) == null) return true;
    else return false;
  }

  public boolean checkUserEmailAvailable(User user, UserRepository userRepository) {
    // System.out.println(userRepository.getUserWithEmail(user.getEmail()).toString());
    if (userRepository.getUserWithEmail(user.getEmail()) == null) return true;
    else return false;
  }

  public boolean checkUserUnique(User user, UserRepository userRepository) {
    if (checkUserLoginAvailable(user, userRepository) &&
      checkUserEmailAvailable(user, userRepository) ) return true;
    else return false;
  }
}
