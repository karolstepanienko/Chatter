package com.chatter.model.Post;

import lombok.Data;

/**
 * Like is used to provide the information needed to change values in the database,
 * after user has liked/unliked
 */
@Data
public class Like {

  /**
   * if status = true - post is liked
   * if status = false - post is unliked
   */
  public boolean status;

  /**
   * user - id of user who makes changes
   * 
   */
  private Integer user;

  /**
   * post - id of post which is liked/unliked
   */
  private Integer post;
  
}
