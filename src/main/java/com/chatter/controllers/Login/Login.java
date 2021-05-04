package com.chatter.controllers.Login;

// Project imports
import com.chatter.model.User.User;
import com.chatter.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account/login")
public class Login {

  @Autowired  
  private UserRepository userRepository;

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
}
