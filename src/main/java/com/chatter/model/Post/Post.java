package com.chatter.model.Post;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.chatter.model.User.User;

import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Post implements Serializable {

  /**
   * Post ID. Used to identify post in database.
   * Generated automatically.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  /**
   * Post creator ID.
   * Thats the ID of the user who created the post.
   */
  private Integer creatorId;

  /**
   * The post contents.
   */
  private String text;

  /**
   * Post privacy setting.
   */
  private String privacy;

  /**
   * Post number of likes.
   */
  private Integer likes;
  // Users that liked this post

  /**
   * Set of users that liked this post.
   * Used as a relation in database.
   */
  @ManyToMany(mappedBy = "posts")
  @JsonIgnore
  private Set<User> users;

  /**
   * Public constructor.
   */
  public Post() { }

  /**
   * Executed when user likes the post
   * Adds the user to set of users who liked this post.
   * @param user User object that will be added.
   */
  public void addUser(final User user) {
    if (this.users == null) {
      this.users = new HashSet<User>();
    }
    this.users.add(user);
  }

  /**
   * Executed when user unlikes the post
   * Removes the user form set of users who liked this post.
   * @param user User object that will be removed.
   */
  public void removeUser(final User user) {
    this.users.remove(user);
  }

  /**
   * Constructor.
   * @param newCreatorId
   * @param newText
   * @param newPrivacy
   * @param newLikes
   */
  public Post(
      final Integer newCreatorId,
      final String newText,
      final String newPrivacy,
      final Integer newLikes) {
    this.creatorId = newCreatorId;
    this.text = newText;
    this.privacy = newPrivacy;
    this.likes = newLikes;
  }

  // Getters

  /**
   * Post ID getter.
   * @return Post ID.
   */
  public Integer getId() {
    return this.id;
  }

  /**
   * Post creator ID getter.
   * @return Post creator ID.
   */
  public Integer getCreatorId() {
    return this.creatorId;
  }

  /**
   * Post content getter.
   * @return Post content text.
   */
  public String getText() {
    return this.text;
  }

  /**
   * Post privacy getter.
   * @return Post privacy setting.
   */
  public String getPrivacy() {
    return this.privacy;
  }

  /**
   * Likes amount getter.
   * @return Number of likes under a post.
   */
  public Integer getLikes() {
    return this.likes;
  }

  /**
   * Set of users who liked the post getter.
   * @return Set of users who liked the post.
   */
  public Set<User> getUsers() {
    return this.users;
  }

  // Setters

  /**
   * Post ID setter.
   * @param newId New post ID.
   */
  public void setId(final Integer newId) {
    this.id = newId;
  }

  /**
   * Post creator ID setter.
   * @param newCreatorId New post creator ID.
   */
  public void setCreatorId(final Integer newCreatorId) {
    this.creatorId = newCreatorId;
  }

  /**
   * Post content setter.
   * @param newText New post content.
   */
  public void setText(final String newText) {
    this.text = newText;
  }

  /**
   * Post privacy setter.
   * @param newPrivacy New post privacy.
   */
  public void setPrivacy(final String newPrivacy) {
    this.privacy = newPrivacy;
  }

  /**
   * Post like amount setter.
   * @param newLikes New post likes amount.
   */
  public void setLikes(final Integer newLikes) {
    this.likes = newLikes;
  }

  /**
   * Serialises post object.
   * @return Post object as a string.
   */
  public String toString() {
    return "Id: " + this.id + ", "
    + "Creator id: " + this.creatorId + ", "
    + "Privacy: " + this.privacy + ", "
    + "Text: " + this.text
    + "Likes:" + this.likes;
  }
}
