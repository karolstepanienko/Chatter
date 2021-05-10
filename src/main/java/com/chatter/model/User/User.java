package com.chatter.model.User;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.Set;

// Project imports:
import com.chatter.model.Post.Post;

import lombok.Data;

// This tells Hibernate to make a table in database out of this class
// Hibernate automatically translates the entity into a table.
@Data
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String userName;
  private String login;
  private String email;
  private String passwordHash;
  private String role;
  @OneToMany(targetEntity=Post.class)
  private Set<Post> postList;

  public User() {}

  public User(String userName, String login, String email, String passwordHash) {
    this.userName = userName;
    this.login = login;
    this.email = email;
    this.passwordHash = passwordHash;
  }
}