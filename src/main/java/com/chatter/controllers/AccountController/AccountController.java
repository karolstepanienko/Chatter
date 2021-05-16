package com.chatter.controllers.AccountController;

// Spring-boot imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

// Project imports
import com.chatter.model.User.User;
import com.chatter.model.User.UserDTO;
import com.chatter.repositories.UserRepository;
// import com.chatter.model.Post.Change;

@RestController
@RequestMapping("/api/account")
public final class AccountController {

  /**
   * User repository used to communicate with database.
   * Makes user related changes.
   * */
  @Autowired
  private UserRepository userRepository;

  /**
   * Password encoder used to create password
   * hashes and compare user submited passwords
   * to hashes stored in database.
   * */
  @Autowired
  private PasswordEncoder passwordEncoder;

  /**
   * Acoount controller logic.
   * Class that provides simple logic to AccountController.
   */
  private AccountControllerLogic acLogic;

  AccountController() {
    acLogic = new AccountControllerLogic();
  }

  /**
   * @HTTPRequestMethod POST
   * Checks against the database if provided userName is available.
   * @param user Given user with provided userName.
   * @return True if userName is available. False otherwise.
   */
  @CrossOrigin()
  @PostMapping("/register/check/username")
  public boolean checkUserUserNameAvailable(@RequestBody final User user) {
    return this.acLogic.checkUserNameAvailable(user, userRepository);
  }

  /**
   * @HTTPRequestMethod POST
   * Checks against the database if provided email is available.
   * @param user Given user with provided email.
   * @return True if email is available. False otherwise.
   */
  @CrossOrigin()
  @PostMapping("/register/check/email")
  public boolean checkUserEmailAvailable(@RequestBody final User user) {
    return this.acLogic.checkUserEmailAvailable(user, userRepository);
  }

  /**
   * @HTTPRequestMethod POST
   * Endpoint used to add user to the database.
   * @param userDTO User data transfer object with necessary user information.
   */
  @CrossOrigin
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/register/add/user")
  public void addUser(@RequestBody final UserDTO userDTO) {

    User user = this.acLogic.createUserFromUserDTO(
      userDTO,
      this.passwordEncoder.encode(userDTO.getPassword()));

    if (this.acLogic.checkUserUnique(user, userRepository)) {
      userRepository.save(user);
    }
  }

  /**
   * @HTTPRequestMethod GET
   * Returns user userName when provided user database ID.
   * @param id Provided ID.
   * @return User with a given ID.
   */
  @CrossOrigin
  @GetMapping("/getUserName")
  @ResponseBody
  public String getUserNameWithId(@RequestParam final Integer id) {
    User user = this.userRepository.getUserWithId(id);
    if (user != null) {
      return user.getUserName();
    } else {
      return null;
    }
  }

  
  /**
   * @HTTPRequestMethod POST
   * Deletes user with a provided ID.
   * @param id Provided ID of a user that will be deleted.
   * @return True if delete operation was successfull. False otherwise.
   */
  @CrossOrigin
  @DeleteMapping("/user/delete/by/id")
  public boolean deleteUserById(@RequestParam final Integer id) {
    User user = userRepository.getUserWithId(id);
    if (user != null) {
      userRepository.delete(user);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Deletes user with a provided userName.
   * @param userName Provided ID of a user that will be deleted.
   * @return True if delete operation was successfull. False otherwise.
   */
  @CrossOrigin
  @DeleteMapping("/user/delete/by/userName")
  public boolean deleteUserByUserName(@RequestParam final String userName) {
    System.out.println(userName);
    User user = userRepository.getUserWithUserName(userName);
    if (user != null) {
      userRepository.delete(user);
      return true;
    } else {
      return false;
    }
  }
}
