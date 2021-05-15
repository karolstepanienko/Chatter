package com.chatter.Configuration.WebSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  // Authorization setup
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf().disable().cors().configurationSource(
        request -> new CorsConfiguration().applyPermitDefaultValues()
      )
        .and()
      .authorizeRequests()
        .antMatchers("/debug/**", "/api/**").permitAll()
        // .antMatchers("/user").hasAnyRole("USER", "ADMIN")
        // .antMatchers("/admin").hasAnyRole("ADMIN")
        .and()
      .formLogin();
      // .anyRequest().authenticated();
      // .addFilter(new AuthenticationFilter(authenticationManager()))
      // .addFilter(new AuthorizationFilter(authenticationManager()))
      // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }
  /**
   * Enables CORS - Cross-origin resource sharing for whole project.
   * @return CorsConfiguration
   */
  @Bean
  CorsConfigurationSource corsConfigurationSource()
  {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**",new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }
}
