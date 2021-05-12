package com.chatter.controllers.DebugController;

// Project imports
import com.chatter.model.Constants.Roles;
import com.chatter.model.User.User;
import com.chatter.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debug")
public class DebugController {

  @Autowired  
  private UserRepository userRepository;

  @CrossOrigin
  @GetMapping("/all")
  public @ResponseBody Iterable<User> getAllUsers() {
    // This returns a JSON or XML with the users
    return userRepository.findAll();
  }

  @CrossOrigin
  @GetMapping("/test")
  public void testPrint() {
    System.out.println(Roles.getUserRole());
    System.out.println(Roles.getAdminRole());
    System.out.println(Roles.getAllRoles());
  }
}
