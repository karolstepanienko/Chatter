package com.chatter.controllers.AccountController;


import com.chatter.classes.User.User;
import com.chatter.repositories.UserRepository;


public class AccountControllerLogic {
  public void deleteUser(User user, UserRepository userRepository) {
    user.setId(userRepository.getUserWithName(user.getName()).getId());
    userRepository.delete(user);
  }
}
