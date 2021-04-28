package com.chatter.Login;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Autowired
  ActiveUserStore activeUserStore;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
  throws IOException {
    HttpSession session = request.getSession(false);
    if (session != null) {
        LoggedUser loggedUser = new LoggedUser(authentication.getName(), activeUserStore);
        session.setAttribute("user", loggedUser);
    }
  }
}
