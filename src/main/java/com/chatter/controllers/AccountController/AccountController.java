package com.chatter.controllers.AccountController;

// Spring-boot imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Project imports
import com.chatter.model.User.User;
import com.chatter.model.User.UserDTO;
import com.chatter.repositories.UserRepository;

@RestController
@RequestMapping("/api/account")
public class AccountController {

  @Autowired  
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;


  private AccountControllerLogic acLogic;
  
  AccountController() {
    acLogic = new AccountControllerLogic();
  }

  @CrossOrigin()
  @PostMapping("/register/check/username")
  public boolean checkUserUserNameAvailable(@RequestBody User user) {
    return this.acLogic.checkUserNameAvailable(user, userRepository);
  }

  @CrossOrigin()
  @PostMapping("/register/check/email")
  public boolean checkUserEmailAvailable(@RequestBody User user) {
    return this.acLogic.checkUserEmailAvailable(user, userRepository);
  }

  @CrossOrigin
  @PostMapping("register/add/user")
  public void addUser(@RequestBody UserDTO userDTO) {
    System.out.println(userDTO.toString());
    User user = this.acLogic.createUserFromUserDTO(
      userDTO,
      this.passwordEncoder.encode(userDTO.getPassword()));

    System.out.println(this.acLogic.checkUserNameAvailable(user, userRepository));
    System.out.println(this.acLogic.checkUserEmailAvailable(user, userRepository));

    System.out.println(this.acLogic.checkUserUnique(user, userRepository));
    if (this.acLogic.checkUserUnique(user, userRepository)) {
      userRepository.save(user);
    }
  }
}