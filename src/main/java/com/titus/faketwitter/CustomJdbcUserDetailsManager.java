package com.titus.faketwitter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import com.titus.faketwitter.users.Privilege;
import com.titus.faketwitter.users.User;
import com.titus.faketwitter.users.UserRepository;

@Service
public class CustomJdbcUserDetailsManager extends JdbcUserDetailsManager {

  private final UserRepository userRepository;

  public CustomJdbcUserDetailsManager(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  
  @Override
  protected void addCustomAuthorities(String userName, List<GrantedAuthority> authorities) {
    User user = userRepository.findByUsername(userName);
    Collection<Privilege> privileges = user.getPrivileges();
    List<SimpleGrantedAuthority> grantedAuthorities = 
        privileges.stream().map(p -> new SimpleGrantedAuthority(p.name())).collect(Collectors.toList());
    authorities.addAll(grantedAuthorities);
    
  }
  
  
}
