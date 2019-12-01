package com.titus.faketwitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
      BCryptPasswordEncoder bCryptPasswordEncoder = 
          new BCryptPasswordEncoder();
      return bCryptPasswordEncoder;
  }
  
  @Autowired
  private CustomJdbcUserDetailsManager customJdbcUserDetailsManager;
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth)
    throws Exception {

      JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> jdbcAuthentication = auth.jdbcAuthentication();
      auth.userDetailsService(customJdbcUserDetailsManager);
      
    
    
      auth
        .inMemoryAuthentication()
        .withUser("user")
          .password("password")
          .roles("USER")
          .and()
        .withUser("admin")
          .password("admin")
          .roles("USER", "ADMIN");
  }

  private JdbcUserDetailsManager foo() {
    return new JdbcUserDetailsManager();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .httpBasic();
  }
  
}
