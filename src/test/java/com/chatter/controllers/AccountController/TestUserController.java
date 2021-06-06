package com.chatter.controllers.AccountController;

import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.chatter.model.User.UserDTO;
import com.chatter.model.User.UserINFO;
import com.chatter.payload.request.LoginRequest;
import com.chatter.payload.request.SignupRequest;

import static com.chatter.model.Constants.AccountPrivacies.getPublicAccess;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestUserController {
  
  @Autowired
  private MockMvc mockMvc;

  @LocalServerPort
  private int port;

  private String link;
  private String tokenType;
  private String accessToken;
  private UserDTO testUser;
  private SignupRequest signupRequest;
  private LoginRequest loginRequest;

  // Adds double quotes
  public static String asJsonString(final Object obj) {
    try {
        return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }

  public void init() {
    this.link = String.join("", "http://localhost:", String.valueOf(this.port), "/api/account");
    this.testUser = new UserDTO();
    this.testUser.setUserName("testFirst");
    this.testUser.setEmail("testEmail@email.com");
    this.testUser.setLogin("testLogin");
    this.testUser.setPassword("12345");
  }

  public void createSignUpRequest() {
    this.signupRequest = new SignupRequest();
    this.signupRequest.setUserName("testFirst");
    this.signupRequest.setEmail("testEmail@email.com");
    this.signupRequest.setRole("USER");
    this.signupRequest.setPassword("12345");
  }

  public void createLoginRequest() {
    this.loginRequest = new LoginRequest();
    this.loginRequest.setUserName("testFirst");
    this.loginRequest.setPassword("12345");
  }

  @Test
  @Transactional
  @Rollback(false)
  @Order(1)
  public void test_1_RegisterTestUser() throws Exception {
    this.init();
    this.createSignUpRequest();

    this.mockMvc.perform(
      post(String.join("", this.link, "/register/add/user"))
        .content(asJsonString(this.signupRequest))
        .contentType(MediaType.APPLICATION_JSON)
        )
      .andExpect(status().isCreated()).andReturn();
  }

  @Test
  @Transactional
  @Rollback(false)
  @Order(2)
  public void test_2_LogUserIn() throws Exception {
    this.init();
    this.createLoginRequest();

    MvcResult mvcResult = this.mockMvc.perform(
      post(String.join("", this.link, "/login/signin"))
        .content(asJsonString(this.loginRequest))
        .contentType(MediaType.APPLICATION_JSON)
        )
      .andExpect(status().isOk())
      .andReturn();

    JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
    this.tokenType = jsonObject.get("tokenType").toString();
    this.accessToken = jsonObject.get("accessToken").toString();
  }

  @Test
  @Order(3)
  public void test_3_getUserId() throws Exception {
    this.init();
    this.test_2_LogUserIn();

    MvcResult mvcResult = this.mockMvc.perform(
      get(String.join("", this.link, "/user/get/id/by/userName"))
        .param("userName", this.testUser.getUserName())
        .header("Authorization", String.join(" ", this.tokenType, this.accessToken))
        )
      .andExpect(status().isOk())
      .andReturn();
    
    // Sets user ID
    this.testUser.setId(Integer.valueOf(mvcResult.getResponse().getContentAsString()));
  }
  
  @Test
  @Order(4)
  public void test_4_SetUserLogin() throws Exception {
    this.init();
    // Sets user ID
    this.test_3_getUserId();
    this.testUser.setLogin("newLogin");

    this.mockMvc.perform(
      post(String.join("", this.link, "/user/update/login"))
      .header("Authorization", String.join(" ", this.tokenType, this.accessToken))
      .content(asJsonString(this.testUser))
      .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(true)));
  }

  @Test
  @Order(5)
  public void test_5_CheckUserNameAvailableFalse() throws Exception {
    this.init();
    this.test_2_LogUserIn();

    this.mockMvc.perform(
      get(String.join("", this.link, "/user/check/username"))
      .param("userName", this.testUser.getUserName())
      .header("Authorization", String.join(" ", this.tokenType, this.accessToken))
      )
    .andExpect(status().isOk())
    // Root of json https://goessner.net/articles/JsonPath/
    .andExpect(jsonPath("$", is(false)));
  }

  @Test
  @Order(6)
  public void test_6_CheckUserNameAvailableTrue() throws Exception {
    this.init();
    this.test_2_LogUserIn();
    String userName = "Thisusernamewlldefinitelybeavailable";

    this.mockMvc.perform(
      get(String.join("", this.link, "/user/check/username"))
      .param("userName", userName)
      .header("Authorization", String.join(" ", this.tokenType, this.accessToken))
      )
    .andExpect(status().isOk())
    // Root of json https://goessner.net/articles/JsonPath/
    .andExpect(jsonPath("$", is(true)));
  }

  @Test
  @Order(7)
  public void test_7_CheckEmailAvailableFalse() throws Exception {
    this.init();
    this.test_2_LogUserIn();
    String email = "testEmail@email.com";

    this.mockMvc.perform(
      get(String.join("", this.link, "/user/check/email"))
      .param("email", email)
      .header("Authorization", String.join(" ", this.tokenType, this.accessToken))
      )
    .andExpect(status().isOk())
    // Root of json https://goessner.net/articles/JsonPath/
    .andExpect(jsonPath("$", is(false)));
  }

  @Test
  @Order(8)
  public void test_8_CheckEmailAvailableTrue() throws Exception {
    this.init();
    this.test_2_LogUserIn();
    String email = "thisEmailWilldefinitelyBeawailable@email.com";

    this.mockMvc.perform(
      get(String.join("", this.link, "/user/check/email"))
      .param("email", email)
      .header("Authorization", String.join(" ", this.tokenType, this.accessToken))
      )
    .andExpect(status().isOk())
    // Root of json https://goessner.net/articles/JsonPath/
    .andExpect(jsonPath("$", is(true)));
  }

  @Test
  @Order(9)
  public void test_9_VerifyUserVerified() throws Exception {
    this.init();
    this.test_2_LogUserIn();

    String userName = "testFirst";
    String password = "12345";

    this.mockMvc.perform(
      get(String.join("", this.link, "/user/get"))
      .param("userName", userName)
      .param("password", password)
      .header("Authorization", String.join(" ", this.tokenType, this.accessToken))
      )
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.userName", is(userName)))
    .andExpect(jsonPath("$.login", is("newLogin")))
    .andExpect(jsonPath("$.email", is("testEmail@email.com")))
    .andExpect(jsonPath("$.accountPrivacy", is("PRIVATE")))
    .andExpect(jsonPath("$.role", is("USER")));
  }

  @Test
  @Order(10)
  public void test_10_VerifyUserUnverified() throws Exception {
    this.init();
    this.test_2_LogUserIn();
    String userName = "testFirst";
    String password = "thispasswordwildefinitelyNotBeGood";

    MvcResult mvcResult = this.mockMvc.perform(
      get(String.join("", this.link, "/user/get"))
      .param("userName", userName)
      .param("password", password)
      .header("Authorization", String.join(" ", this.tokenType, this.accessToken))
      )
    .andExpect(status().isOk())
    .andReturn();

    // Checks if the response was empty
    assertEquals(mvcResult.getResponse().getContentAsString(), "");
  }

  @Test
  @Order(11)
  public void test_11_getUserWithUserNameTrue() throws Exception {
    this.init();
    this.test_2_LogUserIn();
    String userName = "testFirst";

    this.mockMvc.perform(
      get(String.join("", this.link, "/user/get/user/by/userName"))
      .param("userName", userName)
      .header("Authorization", String.join(" ", this.tokenType, this.accessToken))
      )
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.userName", is(userName)))
    .andExpect(jsonPath("$.login", is("newLogin")))
    .andExpect(jsonPath("$.email", is("testEmail@email.com")))
    .andExpect(jsonPath("$.accountPrivacy", is("PRIVATE")))
    .andExpect(jsonPath("$.role", is("USER")));
  }

  @Test
  @Order(12)
  public void test_12_getUserWithUserNameNull() throws Exception {
    this.init();
    this.test_2_LogUserIn();
    String userName = "thisUserDefinitelydoesnotExist";

    MvcResult mvcResult = this.mockMvc.perform(
      get(String.join("", this.link, "/user/get/user/by/userName"))
      .param("userName", userName)
      .header("Authorization", String.join(" ", this.tokenType, this.accessToken))
      )
    .andExpect(status().isOk())
    .andReturn();

    // Checks if the response was empty
    assertEquals(mvcResult.getResponse().getContentAsString(), "");
  }

  @Test
  @Order(13)
  public void test_13_SetUserEmail() throws Exception {
    this.init();
    // Sets user ID
    this.test_3_getUserId();
    this.testUser.setEmail("newTestEmail@email.com");

    this.mockMvc.perform(
      post(String.join("", this.link, "/user/update/email"))
      .content(asJsonString(this.testUser))
      .contentType(MediaType.APPLICATION_JSON)
      .header("Authorization", String.join(" ", this.tokenType, this.accessToken))
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(true)));
  }

  @Test
  @Order(14)
  public void test_14_SetUserPrivacy() throws Exception {
    this.init();
    // Sets user ID
    this.test_3_getUserId();
    UserINFO userINFO = new UserINFO();
    userINFO.setId(this.testUser.getId());
    userINFO.setAccountPrivacy(getPublicAccess());

    this.mockMvc.perform(
      post(String.join("", this.link, "/user/update/privacy"))
      .content(asJsonString(userINFO))
      .contentType(MediaType.APPLICATION_JSON)
      .header("Authorization", String.join(" ", this.tokenType, this.accessToken))
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(true)));
  }

  @Test 
  @Order(15)
  public void test_15_RemoveUserByUserName() throws Exception {
    this.init();
    this.test_2_LogUserIn();

    this.mockMvc.perform(
      post(String.join("", this.link, "/user/delete/by/userName"))
        .param("userName", this.testUser.getUserName())
        .header("Authorization", String.join(" ", this.tokenType, this.accessToken))
        )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(true)));
  }
}
