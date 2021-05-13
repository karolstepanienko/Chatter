package com.chatter.controllers.UserController;

import com.chatter.controllers.AccountController.AccountControllerLogic;
// Project imports
import com.chatter.model.User.User;
import com.chatter.model.User.UserDTO;
import com.chatter.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired  
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private AccountControllerLogic acLogic;
  
  UserController() {
    acLogic = new AccountControllerLogic();
  }

  @CrossOrigin
  @GetMapping("/check/username")
  public boolean checkUserNameUnique(@RequestParam String userName) {
    return this.userRepository.getUserWithUserName(userName) == null;
  }

  @CrossOrigin
  @GetMapping("/check/email")
  public boolean checkEmailUnique(@RequestParam String email) {
    System.out.println(email);
    System.out.println(this.userRepository.getUserWithEmail(email) == null);
    return this.userRepository.getUserWithEmail(email) == null;
  }

  @CrossOrigin
  @PostMapping("/get")
  public @ResponseBody User getVerifiedUser(@RequestBody UserDTO userDTO) {
    User user = this.userRepository.getUserWithUserName(userDTO.getUserName());
    if (user != null && this.passwordEncoder.matches(userDTO.getPassword(), user.getPasswordHash())) {
      return user;
    } else return null;
  }

  @CrossOrigin
  @GetMapping("/get/user/by/userName")
  public @ResponseBody User getUserWithUserName(@RequestParam String userName) {
    User user = this.userRepository.getUserWithUserName(userName);
    return user;
  }

  @CrossOrigin
  @PostMapping("/check/password")
  public @ResponseBody boolean checkUserPassword(@RequestBody UserDTO userDTO) {
    User verifiedUser = this.userRepository.getUserWithUserName(userDTO.getUserName());
    if (verifiedUser != null && this.passwordEncoder.matches(userDTO.getPassword(), verifiedUser.getPasswordHash())) {
      return true;
    } else return false;
  }

  @CrossOrigin
  @GetMapping("/get/id/by/userName")
  public @ResponseBody Integer getUserId(@RequestParam String userName) {
    System.out.println(userName);
    User user = this.userRepository.getUserWithUserName(userName);
    if(user != null) return user.getId();
    else return null;
  }

  @CrossOrigin
  @PostMapping("/update/login")
  public boolean updateUserLogin(@RequestBody UserDTO userDTO) {
    User user = this.userRepository.getUserWithId(userDTO.getId());
    if (user != null) {
      user.setLogin(userDTO.getLogin());
      this.userRepository.save(user);
      return true;
    } else return false;
  }

  @CrossOrigin
  @PostMapping("/update/email")
  public boolean updateUserEmail(@RequestBody UserDTO userDTO) {
    User user = this.userRepository.getUserWithId(userDTO.getId());
    user.setEmail(userDTO.getEmail());
    if (user != null &&
    this.acLogic.checkUserEmailAvailable(user, userRepository)) {
      this.userRepository.save(user);
      return true;
    } else return false;
  }
}
