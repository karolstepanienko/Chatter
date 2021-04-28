package com.chatter.controllers.AccountController;

// Spring-boot imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Project imports
import com.chatter.model.User.User;
import com.chatter.repositories.UserRepository;

@RestController
@RequestMapping("/api/account")
public class AccountController {

  private AccountControllerLogic acLogic;
  AccountController() {
    acLogic = new AccountControllerLogic();
  }

  @Autowired  
  private UserRepository userRepository;

  @CrossOrigin()
  @PostMapping("/register/check/login")
  public boolean checkUserLoginAvailable(@RequestBody User user) {
    return this.acLogic.checkUserLoginAvailable(user, userRepository);
  }

  @CrossOrigin()
  @PostMapping("/register/check/email")
  public boolean checkUserEmailAvailable(@RequestBody User user) {
    return this.acLogic.checkUserEmailAvailable(user, userRepository);
  }

  @CrossOrigin
  @PostMapping("register/add/user")
  public void addUser(@RequestBody User user) {
    // System.out.println(user.toString());
    // System.out.println(this.acLogic.checkUserLoginAvailable(user, userRepository));
    // System.out.println(this.acLogic.checkUserEmailAvailable(user, userRepository));

    // System.out.println(this.acLogic.checkUserUnique(user, userRepository));
    if (this.acLogic.checkUserUnique(user, userRepository)) 
      userRepository.save(user);
  }
}