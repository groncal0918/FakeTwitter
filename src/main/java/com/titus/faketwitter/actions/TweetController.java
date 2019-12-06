package com.titus.faketwitter.actions;

import com.titus.faketwitter.tweets.DisplayableTweet;
import com.titus.faketwitter.tweets.Tweet;
import com.titus.faketwitter.tweets.TweetService;
import com.titus.faketwitter.users.User;
import com.titus.faketwitter.users.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TweetController {

  private final UserService userService;
  private final TweetService tweetService;

  @Autowired
  public TweetController(UserService userService, TweetService tweetService) {
    this.userService = userService;
    this.tweetService = tweetService;
  }
  
  @GetMapping(value= {"/tweets", "/"})
  public String getFeed(@RequestParam(name = "tag", required = false) String tag,
                        @RequestParam(name = "users", required = false) String usernames,
                        @RequestParam(name = "followers", required = false) Boolean followers,
                        Model model) {
      List<DisplayableTweet> tweets = new ArrayList<>();
      if(tag != null) {
        tweets.addAll(tweetService.findAllWithTag(tag));
      } else if(usernames != null) {
        List<User> users = Arrays.asList(usernames.split(",")).stream().map(s -> userService.findByUsername(s)).collect(Collectors.toList());
        tweets.addAll(tweetService.findAllByUsers(users));
      } else if(followers != null && Boolean.TRUE.equals(followers)) {
        User user = userService.getLoggedInUser();
        tweets.addAll(tweetService.findAllByUsers(user.getFollows()));
      } else {
        tweets.addAll(tweetService.findAll());
      }
      
      model.addAttribute("tweetList", tweets);
      return "feed";
  }
  
  @GetMapping(value = "/tweets/new")
  public String getTweetForm(Model model) {
      model.addAttribute("tweet", new Tweet());
      return "newTweet";
  }
  
  @PostMapping(value = "/tweets")
  public String submitTweetForm(@Valid Tweet tweet, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
      if (!bindingResult.hasErrors()) {
          User user = userService.getLoggedInUser();
          tweet.setUser(user);
          tweetService.save(tweet);
          redirectAttributes.addFlashAttribute("successMessage", "Tweet successfully created!");
          return "redirect:/tweets";
      }
      model.addAttribute("tweet", new Tweet());
      return "newTweet";
  }
}
