package com.chatter.controllers.AccountController;

// Spring-boot imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chatter.model.Post.Change;
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
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/register/add/user")
  public void addUser(@RequestBody UserDTO userDTO) {

    User user = this.acLogic.createUserFromUserDTO(
      userDTO,
      this.passwordEncoder.encode(userDTO.getPassword()));

    if (this.acLogic.checkUserUnique(user, userRepository)) {
      userRepository.save(user);
    }
  }

  @CrossOrigin
  @GetMapping("/getlogin")
  @ResponseBody
  public String getLoginwithid(@RequestParam Integer id) {
    User user = this.userRepository.getUserWithId(id);
    if(user != null) return user.getUserName();
    else return null;
  }

  @CrossOrigin
  @PostMapping(value = "/changelogin", consumes = "application/json", produces = "application/json")
  public String changeLogin(@RequestBody Change change) {
    userRepository.changeLogin(change.id, change.login);
    return "account/changelogin";
  }

  @CrossOrigin
  @DeleteMapping("/user/delete/by/id")
  public boolean deleteUserById(@RequestParam Integer id) {
    System.out.println(id);
    User user = userRepository.getUserWithId(id);
    if (user != null) {
      userRepository.delete(user);
      return true;
    }
    else return false;
  }
  
  @CrossOrigin
  @DeleteMapping("/user/delete/by/userName")
  public boolean deleteUserByUserName(@RequestParam String userName) {
    System.out.println(userName);
    User user = userRepository.getUserWithUserName(userName);
    if (user != null) {
      userRepository.delete(user);
      return true;
    }
    else return false;
  }
}