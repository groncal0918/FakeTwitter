package com.titus.faketwitter.users;

import com.titus.faketwitter.users.roles.Role;
import com.titus.faketwitter.users.roles.RoleRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  private final AuthenticatingUserRepository authenticatingRepository;
  
  private final RoleRepository roleRepository;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;


  @Autowired
  public UserService(UserRepository userRepository, AuthenticatingUserRepository authenticatingRepository, 
                     RoleRepository roleRepository,
                     BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.authenticatingRepository = authenticatingRepository;
    this.roleRepository = roleRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public List<User> findAll() {
    return (List<User>)userRepository.findAll();
  }

  public void save(User user) {
    userRepository.save(user);
  }

  public void saveNewUser(UserCreationRequest request) {
    AuthenticatingUser user = new AuthenticatingUser();
    user.setEmail(request.getEmail());
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
    user.setUsername(request.getUsername());

    user.setActive(1);
    Role userRole = roleRepository.findByRole("USER");
    user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
    
    authenticatingRepository.save(user);
  }

  public User getLoggedInUser() {
    String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
    return findByUsername(loggedInUsername);
  }
}
