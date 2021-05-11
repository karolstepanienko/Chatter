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

  @Autowired  
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;


  @CrossOrigin
  @PostMapping("/user/get")
  public @ResponseBody User getVerifiedUser(@RequestBody User unverifiedUser) {
    User verifiedUser = this.userRepository.getUserWithUserName(unverifiedUser.getUserName());
    if (verifiedUser != null){
      System.out.println(verifiedUser);
      return verifiedUser;
    }
    else return null;
  }

  @CrossOrigin
  @PostMapping("/user/check/password")
  public @ResponseBody boolean checkUserPassword(@RequestBody UserDTO userDTO) {
    User verifiedUser = this.userRepository.getUserWithUserName(userDTO.getUserName());
    if (this.passwordEncoder.matches(userDTO.getPassword(), verifiedUser.getPasswordHash())) {
      {return true;}
    }
    else {return false;}
  }

}
