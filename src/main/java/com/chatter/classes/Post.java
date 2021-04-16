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

  public Post() {}

  public Post(Integer creatorId, String text) {
    this.creatorId = creatorId;
    this.text = text;
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

  public String toString() {
    return "Id: " + this.id + ", " +
    "Cretor id: " + this.creatorId + ", " +
    "Text: " + this.text;
  }
  
}
