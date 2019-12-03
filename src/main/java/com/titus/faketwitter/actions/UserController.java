package com.titus.faketwitter.actions;

import com.titus.faketwitter.tweets.Tweet;
import com.titus.faketwitter.tweets.TweetService;
import com.titus.faketwitter.users.User;
import com.titus.faketwitter.users.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

  private final UserService userService;
  private final TweetService tweetService;

  @Autowired
  public UserController(UserService userService, TweetService tweetService) {
    this.userService = userService;
    this.tweetService = tweetService;
  }

  @GetMapping(value = "/users")
  public String getUser(Model model) {
    Map<User, Integer> userTweets = new HashMap<>();
    List<User> users = userService.findAll();
    
    for (User user : users) {
      List<Tweet> tweets = tweetService.findAllByUser(user);
      userTweets.put(user, new Integer(tweets.size()));
    }
    
    model.addAttribute("currentUser", userService.getLoggedInUser());
    model.addAttribute("users", users);
    model.addAttribute("tweetCounts", userTweets);
    return "users";
  }
  
  @GetMapping(value = "/users/{username}")
  public String getUser(@PathVariable(value = "username") String username, Model model) {
    User user = userService.findByUsername(username);
    List<Tweet> tweets = tweetService.findAllByUser(user);
    model.addAttribute("user", user);
    model.addAttribute("tweetList", tweets);
    return "user";
  }
  
  @PostMapping(value = "/users/{username}/follows/{follow}")
  public String followUser(@PathVariable(value = "username") String username, @PathVariable(value = "follow") String follow, Model model, HttpServletRequest request) {
    User user = userService.findByUsername(username);
    User userToFollow = userService.findByUsername(follow);
    user.getFollows().add(userToFollow);
    userService.save(user);
    
    return "redirect:"+ request.getHeader("Referer");
  }
  
  @PostMapping(value = "/users/{username}/unfollow/{follow}")
  public String unfollowUser(@PathVariable(value = "username") String username, @PathVariable(value = "follow") String follow, Model model, HttpServletRequest request) {
    User user = userService.findByUsername(username);
    User userToUnfollow = userService.findByUsername(follow);
    user.getFollows().remove(userToUnfollow);
    userService.save(user);
    
    return "redirect:"+ request.getHeader("Referer");
  }
}
