package com.chatter;

// import com.chatter.Login.Previous.ActiveUserStore;
// import com.chatter.Authentication.DatabaseUserDetailsService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
      System.out.println("Chatter backend app started.");
		};
	}

  @Bean
  public PasswordEncoder encoder() {
      return new BCryptPasswordEncoder();
  }
  
//   @Bean
//   public UserDetailsService userDetailsService() {
//     return new DatabaseUserDetailsService(); // (1)
// }
}
