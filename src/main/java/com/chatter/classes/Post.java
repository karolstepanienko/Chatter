package com.chatter.classes;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// This tells Hibernate to make a table in database out of this class
// Hibernate automatically translates the entity into a table.
@Entity
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
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

  // Getters
  public Integer getId() {
    return this.id;
  }

  public String getText() {
    return this.text;
  }

  public Integer getCreatorId() {
    return this.creatorId;
  }

  public Integer getPrivacy() {
    return this.privacy;
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

  public void setPrivacy(Integer privacy) {
    this.privacy = privacy;
  }

  public String toString() {
    return "Id: " + this.id + ", " +
    "Cretor id: " + this.creatorId + ", " +
    "Privacy: " + this.privacy + ", " +
    "Text: " + this.text;
  }
  
}
