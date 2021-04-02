package com.chatter.controllers.helloController;

// Spring-boot imports:
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

  @RequestMapping("/hello")
  public String folder() {
    return "Hello world!";
  }

}
