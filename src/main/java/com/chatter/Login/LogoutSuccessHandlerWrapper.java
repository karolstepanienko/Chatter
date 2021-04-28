package com.chatter.Login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.core.Authentication;


public class LogoutSuccessHandlerWrapper implements LogoutSuccessHandler {

  @Override
  public void onLogoutSuccess(HttpServletRequest request, 
    HttpServletResponse response, Authentication authentication)
    throws IOException, ServletException {
      HttpSession session = request.getSession();
      if (session != null){
          session.removeAttribute("user");
      }
  }
}
