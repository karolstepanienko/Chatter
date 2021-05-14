package com.chatter.model.Post;

import lombok.Data;

@Data
public class Like {
  public boolean status;
  public Integer user;
  public Integer post;
}
