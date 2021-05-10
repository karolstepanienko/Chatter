package com.chatter.model.Post;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.chatter.model.User.User;

import lombok.Data;

// This tells Hibernate to make a table in database out of this class
// Hibernate automatically translates the entity into a table.
@Data
@Entity
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @ManyToOne
  private User user;
  private Integer creatorId;
  private String text;
  private Integer privacy;

  public Post() {}

  public Post(Integer creatorId, String text, Integer privacy) {
    this.creatorId = creatorId;
    this.text = text;
    // 0 - post is public
    // 1 - post is private
    this.privacy = privacy;
  }  
}
