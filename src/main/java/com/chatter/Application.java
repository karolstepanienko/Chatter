package com.chatter;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

// Project imports
import com.chatter.Login.ActiveUserStore;

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
  public ActiveUserStore activeUserStore(){
    return new ActiveUserStore();
  }
}
