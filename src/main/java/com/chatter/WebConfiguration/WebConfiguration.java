package com.chatter.WebConfiguration;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfiguration implements WebMvcConfigurer {
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    // registry.addViewController("/")
    // .setViewName("home");
    // registry.addViewController("about").setViewName("about");
    // registry.addViewController("/about").setViewName("index.html");
  }
}
