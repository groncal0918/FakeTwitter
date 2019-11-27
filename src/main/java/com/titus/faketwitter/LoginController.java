package com.titus.faketwitter;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

  @GetMapping(value = {"/", "/login"})
  public String getLoginPage() {
    return "login";
  }
  
  @PostMapping(value = "/login", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
  public ModelAndView postLogin(LoginRequest loginRequest, HttpSession session) {
    session.setAttribute("userName", loginRequest.getUserName());
    
    return new ModelAndView("redirect:/main");
  }

}
