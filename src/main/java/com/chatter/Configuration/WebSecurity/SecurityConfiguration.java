package com.chatter.Configuration.WebSecurity;

// import org.springframework.beans.factory.annotation.Autowired;

// import com.chatter.Login.AuthenticationFilter;
// import com.chatter.Login.AuthorizationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  // @Autowired
  // UserDetailsService userDetailsService;

  // Authentication setup
  // @Override
  // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  //   auth.userDetailsService(userDetailsService);
  // }

  // Authorization setup
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf().disable().cors()
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

  // @Bean
  // protected PasswordEncoder getPasswordEncoder() {
  //   return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
  // }

  @Bean
  CorsConfigurationSource corsConfigurationSource()
  {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**",new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }
}
