package com.chatter.controllers.AccountController;

// Spring-boot imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.chatter.model.Constants.AccountPrivacies;
import com.chatter.model.Constants.Roles;
// Project imports
import com.chatter.model.User.User;
import com.chatter.payload.request.SignupRequest;
import com.chatter.payload.response.MessageResponse;
import com.chatter.repositories.UserRepository;

@RestController
@RequestMapping("/api/account")
public class AccountController {

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

  // /**
  //  * Acoount controller logic.
  //  * Class that provides simple logic to AccountController.
  //  */
  // private AccountControllerLogic acLogic;

  // AccountController() {
  //   acLogic = new AccountControllerLogic();
  // }

  /**
   * @HTTPRequestMethod POST
   * Checks against the database if provided userName is available.
   * @param user Given user with provided userName.
   * @return True if userName is available. False otherwise.
   */
  @CrossOrigin()
  @PostMapping("/register/check/username")
  public boolean checkUserUserNameAvailable(@RequestBody final User user) {
    if (this.userRepository.existsByUserName(user.getUserName())) {
      return false;
    } else return true;
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
    if (this.userRepository.existsByUserName(user.getEmail())) {
      return false;
    } else return true;
  }

  @CrossOrigin
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/register/add/user")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUserName(signUpRequest.getUserName())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(
      signUpRequest.getUserName(),
      "",
      signUpRequest.getEmail(),
      passwordEncoder.encode(signUpRequest.getPassword()));

		String role = signUpRequest.getRole();
		// Set<Role> roles = new HashSet<>();

    if (Roles.roleExists(role)) {
      user.setRole(role);
    } else {
      return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Role does not exist."));
    }
    user.setAccountPrivacy(AccountPrivacies.getPrivateAccess());

		userRepository.save(user);
    // ResponseEntity<?> response = ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    // response.status(HttpStatus.CREATED);

		return new ResponseEntity<>(new MessageResponse("User registered successfully!"), HttpStatus.CREATED);
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
  @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
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
   * @Tested
   * @param userName Provided ID of a user that will be deleted.
   * @return True if delete operation was successfull. False otherwise.
   */
  @CrossOrigin
  @PreAuthorize("hasAuthority('USER')")
  @PostMapping("/user/delete/by/userName")
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
