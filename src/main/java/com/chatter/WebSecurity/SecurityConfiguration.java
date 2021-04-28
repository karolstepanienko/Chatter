package com.chatter.WebSecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf() // Needs to be turned on in react
      .disable()
      .cors()
        .and()
      .authorizeRequests()
        .antMatchers("/debug", "/api").permitAll()
        .and()
      .formLogin()
        .permitAll()
        .and()
      .logout()
        .permitAll();
  }
}
