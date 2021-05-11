package com.chatter.controllers.AccountController;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.chatter.model.User.User;
import com.chatter.model.User.UserDTO;

@TestMethodOrder( MethodOrderer.MethodName.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestAccountController {
  
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
  public void test_2_CheckUserNameAvailableFalse() throws Exception {
    this.init();

    this.mockMvc.perform(
      post(String.join("", this.link, "/register/check/username/only"))
      .content(this.testUser.getUserName())
      )
    .andExpect(status().isOk())
    // Root of json https://goessner.net/articles/JsonPath/
    .andExpect(jsonPath("$", is(false)));
  }

  @Test
  public void test_3_CheckUserNameAvailableTrue() throws Exception {
    this.init();
    String userName = "Thisusernamewlldefinitelybeavailable";

    this.mockMvc.perform(
      post(String.join("", this.link, "/register/check/username/only"))
      .content(userName)
      )
    .andExpect(status().isOk())
    // Root of json https://goessner.net/articles/JsonPath/
    .andExpect(jsonPath("$", is(true)));
  }

  @Test void test_4_RemoveUserByUserName() throws Exception {
    this.init();

    this.mockMvc.perform(
      delete(String.join("", this.link, "/user/delete/by/userName"))
        .param("userName", this.testUser.getUserName())
        )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(true)));
  }

}
