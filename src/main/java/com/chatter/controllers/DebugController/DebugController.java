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

/**
 * Controller with various development
 * and debugging related endpoints.
 */
@RestController
@RequestMapping("/debug")
public class DebugController {

  /**
   * User repository used to communicate with database.
   * Makes user related changes.
   * */
  @Autowired
  private UserRepository userRepository;

  /**
   * @HTTPRequestMethod GET
   * Returns all users registered in database.
   * @return JSON with all users.
   */
  @CrossOrigin
  @GetMapping("/all")
  public @ResponseBody Iterable<User> getAllUsers() {
    return userRepository.findAll();
  }

  /**
   * @HTTPRequestMethod GET
   * Test print method.
   */
  @CrossOrigin
  @GetMapping("/test")
  public void testPrint() {
    System.out.println(Roles.getUserRole());
    System.out.println(Roles.getAdminRole());
    System.out.println(Roles.getAllRoles());
  }
}
