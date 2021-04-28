package com.chatter.Login;

import java.util.ArrayList;
import java.util.List;

// Project imports
import com.chatter.model.User.User;

public class ActiveUserStore {

  public List<User> users;
  
  public ActiveUserStore() {
    users = new ArrayList<User>();
  }

  public void addUser(User user) {
    this.users.add(user);
  }

  public void setUsers(List<User> users){
    this.users = users;
  }

  public List<User> getUsers() {
    return this.users;
  }
}
