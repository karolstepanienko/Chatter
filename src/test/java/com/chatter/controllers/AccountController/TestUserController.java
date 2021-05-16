package com.chatter.controllers.AccountController;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.assertEquals;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.chatter.model.User.UserDTO;
import com.chatter.model.Post.Post;

@TestMethodOrder( MethodOrderer.MethodName.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestUserController {
  
  @Autowired
  private MockMvc mockMvc;

  @LocalServerPort
  private int port;

  private String link;
  private UserDTO testUser;

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

  @Test
  public void test_1_RegisterTestUser() throws Exception {
    this.init();

    this.mockMvc.perform(
      post(String.join("", this.link, "/register/add/user"))
        .content(asJsonString(this.testUser))
        .contentType(MediaType.APPLICATION_JSON)
        )
      .andExpect(status().isCreated());
  }
  
  @Test 
  public void test_2_getUserId() throws Exception {
    this.init();

    MvcResult mvcResult = this.mockMvc.perform(
      get(String.join("", this.link, "/user/get/id/by/userName"))
        .param("userName", this.testUser.getUserName())
        )
      .andExpect(status().isOk())
      .andReturn();
    
    // Sets user ID
    this.testUser.setId(Integer.valueOf(mvcResult.getResponse().getContentAsString()));
  }
  
  @Test 
  public void test_3_SetUserLogin() throws Exception {
    this.init();
    // Sets user ID
    this.test_2_getUserId();
    this.testUser.setLogin("newLogin");

    this.mockMvc.perform(
      post(String.join("", this.link, "/user/update/login"))
      .content(asJsonString(this.testUser))
      .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(true)));
  }

  @Test
  public void test_4_CheckUserNameAvailableFalse() throws Exception {
    this.init();
    

    this.mockMvc.perform(
      get(String.join("", this.link, "/user/check/username"))
      .param("userName", this.testUser.getUserName())
      )
    .andExpect(status().isOk())
    // Root of json https://goessner.net/articles/JsonPath/
    .andExpect(jsonPath("$", is(false)));
  }

  @Test
  public void test_5_CheckUserNameAvailableTrue() throws Exception {
    this.init();
    String userName = "Thisusernamewlldefinitelybeavailable";

    this.mockMvc.perform(
      get(String.join("", this.link, "/user/check/username"))
      .param("userName", userName)
      )
    .andExpect(status().isOk())
    // Root of json https://goessner.net/articles/JsonPath/
    .andExpect(jsonPath("$", is(true)));
  }

  @Test
  public void test_6_CheckEmailAvailableFalse() throws Exception {
    this.init();
    String email = "testEmail@email.com";

    this.mockMvc.perform(
      get(String.join("", this.link, "/user/check/email"))
      .param("email", email)
      )
    .andExpect(status().isOk())
    // Root of json https://goessner.net/articles/JsonPath/
    .andExpect(jsonPath("$", is(false)));
  }

  @Test
  public void test_7_CheckEmailAvailableTrue() throws Exception {
    this.init();
    String email = "thisEmailWilldefinitelyBeawailable@email.com";

    this.mockMvc.perform(
      get(String.join("", this.link, "/user/check/email"))
      .param("email", email)
      )
    .andExpect(status().isOk())
    // Root of json https://goessner.net/articles/JsonPath/
    .andExpect(jsonPath("$", is(true)));
  }

  @Test
  public void test_8_VerifyUserVerified() throws Exception {
    this.init();
    String userName = "testFirst";
    String password = "12345";

    this.mockMvc.perform(
      get(String.join("", this.link, "/user/get"))
      .param("userName", userName)
      .param("password", password)
      )
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.userName", is(userName)))
    .andExpect(jsonPath("$.login", is("newLogin")))
    .andExpect(jsonPath("$.email", is("testEmail@email.com")))
    .andExpect(jsonPath("$.accountPrivacy", is("PRIVATE")))
    .andExpect(jsonPath("$.role", is("USER")));
  }

  @Test
  public void test_9_VerifyUserUnverified() throws Exception {
    this.init();
    String userName = "testFirst";
    String password = "thispasswordwildefinitelyNotBeGood";

    MvcResult mvcResult = this.mockMvc.perform(
      get(String.join("", this.link, "/user/get"))
      .param("userName", userName)
      .param("password", password)
      )
    .andExpect(status().isOk())
    .andReturn();

    // Checks if the response was empty
    assertEquals(mvcResult.getResponse().getContentAsString(), "");
  }

  @Test
  public void test_10_getUserWithUserNameTrue() throws Exception {
    this.init();
    String userName = "testFirst";

    this.mockMvc.perform(
      get(String.join("", this.link, "/user/get/user/by/userName"))
      .param("userName", userName)
      )
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.userName", is(userName)))
    .andExpect(jsonPath("$.login", is("newLogin")))
    .andExpect(jsonPath("$.email", is("testEmail@email.com")))
    .andExpect(jsonPath("$.accountPrivacy", is("PRIVATE")))
    .andExpect(jsonPath("$.role", is("USER")));
  }

  @Test
  public void test_11_getUserWithUserNameNull() throws Exception {
    this.init();
    String userName = "thisUserDefinitelydoesnotExist";

    MvcResult mvcResult = this.mockMvc.perform(
      get(String.join("", this.link, "/user/get/user/by/userName"))
      .param("userName", userName)
      )
    .andExpect(status().isOk())
    .andReturn();

    // Checks if the response was empty
    assertEquals(mvcResult.getResponse().getContentAsString(), "");
  }

  @Test void test_12_RemoveUserByUserName() throws Exception {
    this.init();

    this.mockMvc.perform(
      post(String.join("", this.link, "/user/delete/by/userName"))
        .param("userName", this.testUser.getUserName())
        )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(true)));
  }
}
