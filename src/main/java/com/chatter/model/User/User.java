package com.chatter.model.User;

import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Column;


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
  @Column(nullable=false)
  private Integer id;
  private Post post;
  private String userName;
  private String login;
  private String email;
  private String passwordHash;
  private String accountPrivacy;
  private String role;
  @ManyToMany(mappedBy = "users")
  private Set<Post> posts;

  public User() {}

  public User(String userName, String login, String email, String passwordHash) {
    this.userName = userName;
    this.login = login;
    this.email = email;
    this.passwordHash = passwordHash;
  }
}