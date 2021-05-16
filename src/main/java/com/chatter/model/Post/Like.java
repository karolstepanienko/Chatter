package com.chatter.model.Post;

import lombok.Data;

@Data
public class Like {

  /**
   * ??? TODO
   * No wes czemu to publiczne
   */
  public boolean status;

  /**
   * ???
   */
  public Integer user;

  /**
   * ???
   */
  public Integer post;
}
