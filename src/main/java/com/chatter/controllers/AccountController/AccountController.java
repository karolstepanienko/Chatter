package com.chatter.controllers.AccountController;

// Spring-boot imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

  @GetMapping("/register")
  public String registerForm(Model model) {
    model.addAttribute("register_user", new User());
    return "account/register";
  }

  @PostMapping("/register")
  public String registerSubmit(@ModelAttribute User user, Model model) {
    model.addAttribute("register_user", user);
    // Saves user in db
    userRepository.save(user);
    // Returns the result view
    // Add @ ResponseBody before fuction return type to return String
    return "account/register_result";
  }

  @GetMapping("/delete")
  public String deleteForm(Model model) {
    model.addAttribute("account_delete", new User());
    return "account/delete";
  }

  @PostMapping("/delete")
  public String deleteAccount(@ModelAttribute User user, Model model) {
    model.addAttribute("account_delete", user);
    // Finds the user ID and deletes the user
    acLogic.deleteUser(user, userRepository);
    return "account/delete_result";
  }

  @GetMapping("/getlogin")
  @ResponseBody
  public String getLoginwithid(@RequestParam Integer id) {
    User user = this.userRepository.getUserWithId(id);
    if(user != null) return user.getUserName();
    else return null;
  }

  @PostMapping(value = "/changelogin", consumes = "application/json", produces = "application/json")
  public String changeLogin(@RequestBody Change change) {
    userRepository.changeLogin(change.id, change.login);
    return "account/changelogin";
  }
}