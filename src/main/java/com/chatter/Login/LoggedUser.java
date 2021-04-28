package com.chatter.Login;

import java.util.List;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

// Project imports
import com.chatter.model.User.User;
import com.chatter.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class LoggedUser implements HttpSessionBindingListener{

  private User user;
  private ActiveUserStore activeUserStore;

  @Autowired
  private UserRepository userRepository;
  
  public LoggedUser(User user, ActiveUserStore activeUserStore) {
    this.user = user;
    this.activeUserStore = activeUserStore;
  }

  public LoggedUser(String login, ActiveUserStore activeUserStore) {
    this.user = this.userRepository.getUserWithLogin(login);
    this.activeUserStore = activeUserStore;
  }

  public LoggedUser() {}

  @Override
  public void valueBound(HttpSessionBindingEvent event) {
      List<User> users = activeUserStore.getUsers();
      LoggedUser loggedInUser = (LoggedUser) event.getValue();
      if (!users.contains(loggedInUser.getUser())) {
          users.add(loggedInUser.getUser());
      }
  }

  @Override
  public void valueUnbound(HttpSessionBindingEvent event) {
      List<User> users = activeUserStore.getUsers();
      LoggedUser loggedInUser = (LoggedUser) event.getValue();
      if (users.contains(loggedInUser.getUser())) {
          users.remove(loggedInUser.getUser());
      }
  }

  public User getUser() {
    return this.user;
  }
}
