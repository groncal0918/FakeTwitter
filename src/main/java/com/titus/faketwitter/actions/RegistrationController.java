package com.titus.faketwitter.actions;

import com.titus.faketwitter.users.User;
import com.titus.faketwitter.users.UserService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

  private final UserService userService;

  @Autowired
  public RegistrationController(UserService userService) {
    this.userService = userService;
  }
  
  @GetMapping(value="/signup")
  public String registration(Model model){
      User user = new User();
      model.addAttribute("user", user);
      return "registration";
  }

  @PostMapping(value = "/signup")
  public String createNewUser(@Valid User user, BindingResult bindingResult, Model model) {
      User userExists = userService.findByUsername(user.getUsername());
      if (userExists != null) {
          bindingResult.rejectValue("username", "error.user", "Username is already taken");
      }
      if (!bindingResult.hasErrors()) {
          userService.saveNewUser(user);
          model.addAttribute("success", "Sign up successful!");
          model.addAttribute("user", new User());
      }
      return "registration";
  }
  
}
