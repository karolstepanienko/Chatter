package com.chatter.controllers.LoginController;

import java.util.List;
import java.util.stream.Collectors;

import com.chatter.Security.jwt.JwtUtils;
import com.chatter.Security.services.UserDetailsImplementation;
// Project imports
import com.chatter.model.User.User;
import com.chatter.model.User.UserDTO;
import com.chatter.payload.request.LoginRequest;
import com.chatter.payload.response.JwtResponse;
import com.chatter.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account/login")
public class LoginController {

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

  @Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

  /**
   * @HTTPRequestMethod POST
   * Verifies the given user data against the database.
   * @param unverifiedUser Provided unverified user data.
   * @return Verified user if provided data was correct. False otherwise.
   */
  @CrossOrigin
  @PostMapping("/user/get")
  public @ResponseBody User getVerifiedUser(
    @RequestBody final User unverifiedUser) {
    User verifiedUser = this.userRepository
      .getUserWithUserName(unverifiedUser.getUserName());
    if (verifiedUser != null) {
      System.out.println(verifiedUser);
      return verifiedUser;
    } else {
      return null;
    }
  }

  /**
   * @HTTPRequestMethod POST
   * Checks user password against the data in database.
   * @param userDTO Provided user data.
   * @return True if provided data was correct. False otherwise.
   */
  @CrossOrigin
  @PostMapping("/user/check/password")
  public @ResponseBody boolean checkUserPassword(
    @RequestBody final UserDTO userDTO) {
    User verifiedUser = this.userRepository
      .getUserWithUserName(userDTO.getUserName());
    if (verifiedUser != null) {
      return this.passwordEncoder.matches(
        userDTO.getPassword(),
        verifiedUser.getPasswordHash());
    } else {
      return false;
    }
  }

  /**
   * @HTTPRequestMethod POST
   * Signs user in and creates appropriate JWT token.
   * @Tested
   * @param loginRequest Request object containing user userName and password.
   * @return JWTResponseEntity
   */
  @CrossOrigin
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImplementation userDetails = (UserDetailsImplementation) authentication.getPrincipal();		
    // String role = userDetails.getAuthorities().stream().toList().toString();
		List<String> roles = userDetails.getAuthorities()
      .stream()
      .map(item -> item.getAuthority())
      .collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(
      jwt, 
      userDetails.getId(), 
      userDetails.getUsername(), 
      userDetails.getEmail(), 
      roles));
	}

  /**
   * @HTTPRequestMethod GET
   * @Tested
   * Verifies credentials provided on login.
   * @param userName Provided userName.
   * @param password Provided plain text password.
   * @return Verified user if provided data was correct. False otherwise.
   */
  @CrossOrigin
  @GetMapping("/get")
  public @ResponseBody User getVerifiedUser(
    @RequestParam final String userName,
    @RequestParam final String password) {
    User user = this.userRepository.getUserWithUserName(userName);
    if (user != null
    && this.passwordEncoder.matches(password, user.getPasswordHash())) {
      return user;
    } else {
      return null;
    }
  }
}
