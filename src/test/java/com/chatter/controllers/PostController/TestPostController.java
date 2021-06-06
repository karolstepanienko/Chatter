package com.chatter.controllers.PostController;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.access.event.PublicInvocationEvent;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.chatter.model.User.UserDTO;
import com.chatter.model.Post.Like;
import com.chatter.model.Post.Post;

@TestMethodOrder( MethodOrderer.MethodName.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestPostController {
  
  @Autowired
  private MockMvc mockMvc;

  @LocalServerPort
  private int port;

  private String link;
  private String link2;
  private Post testPost;
  private Post testPost2;
  private UserDTO testUser;
  private Like testLike;

  // Adds double quotes
  public static String asJsonString(final Object obj) {
    try {
        return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }

  public void init() {
    this.link = String.join("", "http://localhost:", String.valueOf(this.port), "/api/post");
    this.link2 = String.join("", "http://localhost:", String.valueOf(this.port), "/api/account");

    this.testUser = new UserDTO();
    this.testUser.setUserName("testFirst");
    this.testUser.setEmail("testEmail@email.com");
    this.testUser.setLogin("testLogin");
    this.testUser.setPassword("12345");

    this.testPost = new Post();
    this.testPost.setText("text");
    this.testPost.setPrivacy("PUBLIC");
    this.testPost.setLikes(999999998);
    // this.testPost.setCreatorId(this.testUser.getId());

    this.testPost2 = new Post();
    this.testPost2.setText("text2");
    this.testPost2.setPrivacy("PRIVATE");
    this.testPost2.setLikes(999999999);
    // this.testPost2.setCreatorId(this.testUser.getId());


  }
  // @Test
  // @Order(1)
  // public void test_1_CreateTestPost() throws Exception {
  //   this.init();
  //   this.mockMvc.perform(
  //     post(String.join("", this.link2, "/register/add/user"))
  //       .content(asJsonString(this.testUser))
  //       .contentType(MediaType.APPLICATION_JSON)
  //       )
  //     .andExpect(status().isCreated());

  //     MvcResult mvcResult = this.mockMvc.perform(
  //     get(String.join("", this.link2, "/user/get/id/by/userName"))
  //       .param("userName", this.testUser.getUserName())
  //       )
  //     .andExpect(status().isOk())
  //     .andReturn();
    
  //   // Sets user ID
  //   this.testUser.setId(Integer.valueOf(mvcResult.getResponse().getContentAsString()));

  //     this.testPost.setCreatorId(this.testUser.getId());
  //     this.testPost2.setCreatorId(this.testUser.getId());

  //   this.mockMvc.perform(
  //     post(String.join("", this.link, "/addpost"))
  //       .content(asJsonString(this.testPost))
  //       .contentType(MediaType.APPLICATION_JSON)
  //       )
  //     .andExpect(status().isCreated());

  //     this.mockMvc.perform(
  //     post(String.join("", this.link, "/addpost"))
  //       .content(asJsonString(this.testPost2))
  //       .contentType(MediaType.APPLICATION_JSON)
  //       )
  //     .andExpect(status().isCreated());
  // }

  // @Test 
  // @Order(2)
  // public void test_2_getPosts() throws Exception {
  //   this.init();
  //   this.test_1_CreateTestPost();
  //   MvcResult mvcResult = this.mockMvc.perform(
  //     get(String.join("", this.link, "/allposts"))
  //       )
  //     .andExpect(status().isOk())
  //     .andExpect(jsonPath("$[0].creatorId", is(this.testPost.getCreatorId())))
  //     .andExpect(jsonPath("$[0].text", is(this.testPost.getText())))
  //     .andExpect(jsonPath("$[0].privacy", is(this.testPost.getPrivacy())))
  //     .andExpect(jsonPath("$[0].likes", is(this.testPost.getLikes())))
  //     .andReturn();
  //     // Sets post ID
  //     String response = mvcResult.getResponse().getContentAsString();
  //     Integer id = JsonPath.parse(response).read("$[0].id");
  //     this.testPost.setId(id);

  // }

  // @Test 
  // @Order(3)
  // public void test_3_Like() throws Exception {
  //   this.init();
  //   this.test_2_getPosts();
  //   this.testLike = new Like();
  //   this.testLike.setStatus(false);
  //   this.testLike.setPost(this.testPost.getId());
  //   this.testLike.setUser(this.testPost.getCreatorId());

  //   this.mockMvc.perform(
  //     post(String.join("", this.link, "/like"))
  //       .content(asJsonString(this.testLike))
  //       .contentType(MediaType.APPLICATION_JSON)
  //       )
  //     .andExpect(status().isOk());

  //   this.mockMvc.perform(
  //     get(String.join("", this.link, "/allposts"))
  //       )
  //     .andExpect(status().isOk())
  //     .andExpect(jsonPath("$[?(@.id == "+ this.testPost.getId().toString() 
  //       + " )].likes").value(containsInAnyOrder(this.testPost.getLikes()-1)))
  //     .andReturn();
  // }
   
  // @Test
  // @Order(4)
  // public void test_4_UpdatePostPrivacy() throws Exception {
  //   this.init();
  //   this.test_2_getPosts();

  //   this.mockMvc.perform(
  //     post(String.join("", this.link, "/update/privacy/" , (this.testPost.getId()).toString()))
  //       .content(asJsonString(this.testPost))
  //       .contentType(MediaType.APPLICATION_JSON)
  //       )
  //     .andExpect(status().isOk());

  //   this.testPost2.setPrivacy("PRIVATE");

  //   this.mockMvc.perform(
  //     get(String.join("", this.link, "/allposts"))
  //       )
  //     .andExpect(status().isOk())
  //     .andExpect(jsonPath("$[?(@.id == "+ this.testPost.getId().toString() 
  //       + " )]").doesNotExist())
  //     .andReturn();

  //     this.mockMvc.perform(
  //     post(String.join("", this.link, "/update/privacy/" , (this.testPost.getId()).toString()))
  //       .content(asJsonString(this.testPost))
  //       .contentType(MediaType.APPLICATION_JSON)
  //       )
  //     .andExpect(status().isOk());

  //   this.testPost2.setPrivacy("PUBLIC");
  //   MvcResult mvcResult = this.mockMvc.perform(
  //     get(String.join("", this.link, "/allposts"))
  //       )
  //     .andExpect(status().isOk())
  //     .andExpect(jsonPath("$[?(@.id == "+ this.testPost.getId().toString() 
  //       + " )].privacy").value(containsInAnyOrder(this.testPost.getPrivacy())))
  //     .andReturn();

  // }


  // @Test
  // @Order(5)
  // public void test_5_DeletePost() throws Exception {
  //   this.init();
  //   this.test_2_getPosts();

  //   MvcResult mvcResult = this.mockMvc.perform(
  //     get(String.join("", this.link, "/allposts"))
  //       )
  //     .andExpect(status().isOk())
  //     .andExpect(jsonPath("$[?(@.id == "+ this.testPost.getId().toString() 
  //       + " )].text").value(containsInAnyOrder(this.testPost.getText())))
  //     .andReturn();

  //   this.mockMvc.perform(
  //     post(String.join("", this.link, "/delete/by/Id/", (this.testPost.getId()).toString()))
  //       .content(asJsonString(this.testPost))
  //       .contentType(MediaType.APPLICATION_JSON)
  //       )
  //     .andExpect(status().isOk());

  //   MvcResult mvcResult2 = this.mockMvc.perform(
  //     get(String.join("", this.link, "/allposts"))
  //       )
  //     .andExpect(status().isOk())
  //     .andExpect(jsonPath("$[?(@.id == "+ this.testPost.getId().toString() 
  //       + " )]").doesNotExist())
  //     .andReturn();

      
  //     this.mockMvc.perform(
  //       post(String.join("", this.link, "/delete/", (this.testUser.getId()).toString()))
  //         )
  //       .andExpect(status().isOk());
  // }
}
