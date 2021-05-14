package com.chatter.model.Post;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.chatter.model.User.User;

import java.util.Set;
import lombok.Data;
import java.util.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;

// This tells Hibernate to make a table in database out of this class
// Hibernate automatically translates the entity into a table.

@Entity
public class Post implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private Integer creatorId;
  private String text;
  private Integer privacy;
  private Integer likes;
  // Users that liked this post
  @ManyToMany(mappedBy = "posts")
  @JsonIgnore
  private Set<User> users;

  public Post() {}

  public void addUser(User user) {
    if (this.users == null) this.users = new HashSet<User>();
    this.users.add(user);
  }
  
  public void removeUser(User user){
    this.users.remove(user);
  }

  public Post(Integer creatorId, String text, Integer privacy, Integer likes ) {
    this.creatorId = creatorId;
    this.text = text;
    // 0 - post is public
    // 1 - post is private
    this.privacy = privacy;
    this.likes = likes;
  }  
   // Getters
   public Integer getId() {
    return this.id;
  }
  public Integer getLikes() {
    return this.likes;
  }
  public Integer getPrivacy(){
    return this.privacy;
  }

  public String getText() {
    return this.text;
  }

  public Integer getCreatorId() {
    return this.creatorId;
  }
  public Set<User> getUsers() {
    return this.users;
  }

  // Setters
  public void setId(Integer id) {
    this.id = id;
  }

  public void setCreatorId(Integer creatorId) {
    this.creatorId = creatorId;
  }

  public void setText(String text) {
    this.text = text;
  }
  public void setLikes(Integer likes) {
    this.likes = likes;
  }
  public void setPrivacy(Integer privacy){
    this.privacy = privacy;
  }


  public String toString() {
    return "Id: " + this.id + ", " +
    "Creator id: " + this.creatorId + ", " +
    "Privacy: " + this.privacy + ", " +
    "Text: " + this.text;
  }
  
}

