package com.chatter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application {
  /**
   * Main function that runs chatter backend app.
   * @param args Command-line arguments.
   */
  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }
  /**
   * Informs the user that the backend app was started.
   * @param ctx Refers to started app.
   * @return Printed welcome message.
   */
  @Bean
  public CommandLineRunner commandLineRunner(final ApplicationContext ctx) {
    return args -> {
      System.out.println("Chatter backend app started.");
    };
  }
  /**
   * Password encoder bean.
   * @return New password encoder.
   */
  @Bean
  public PasswordEncoder encoder() {
      return new BCryptPasswordEncoder();
  }
}
