package com.chatter.controllers.helloController;


import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

	@RequestMapping("/")
	public void index(HttpServletResponse response) throws IOException {
		response.sendRedirect("html/index.html");
	}

  @RequestMapping("/hello")
  public String folder() {
    return "Hello world!";
  }

}
