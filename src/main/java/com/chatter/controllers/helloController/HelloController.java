package com.chatter.controllers.helloController;


// Spring-boot imports:
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

// Java imports:
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;


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
