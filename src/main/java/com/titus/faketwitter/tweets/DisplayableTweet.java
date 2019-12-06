package com.titus.faketwitter.tweets;

import com.titus.faketwitter.tweets.hashtags.Hashtag;
import com.titus.faketwitter.users.User;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.ocpsoft.prettytime.PrettyTime;

public class DisplayableTweet {

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yy");
  
  private final Tweet tweet;
  private final PrettyTime prettyTime;
  private final Date now;

  public DisplayableTweet(Tweet tweet, PrettyTime prettyTime, Date now) {
    this.tweet = tweet;
    this.prettyTime = prettyTime;
    this.now = now;
  }
  
  public Long getId() {
    return tweet.getId();
  }
  
  public User getUser() {
    return tweet.getUser();
  }
  
  public String getMessage() {
    String message = tweet.getMessage();
    Set<Hashtag> hashtags = tweet.getHashtags();
    
    if(message == null) {
      return "";
    }
    if(hashtags.isEmpty()) {
      return message;
    }
    String decoratedMessage = message;
    for (Hashtag hashtag : hashtags) {
      String anchor = "<a href=\"/tweets?tag=" + encodedHashtag(hashtag) + "\">" + hashtag.getTag() + "</a>";
      decoratedMessage = decoratedMessage.replace(hashtag.getTag(), anchor);
    }
    
    return decoratedMessage;
  }
  
  private String encodedHashtag(Hashtag hashtag) {
    try {
      return URLEncoder.encode(hashtag.getTag(), StandardCharsets.UTF_8.toString());
    } catch (UnsupportedEncodingException ex) {
      throw new IllegalStateException("Could not url encode " + hashtag + " using " + StandardCharsets.UTF_8, ex);
    }
  }

  public String getCreatedAt() {
    long diffInMillies = Math.abs(now.getTime() - tweet.getCreatedAt().getTime());
    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    if (diff > 3) {
      return dateFormat.format(tweet.getCreatedAt());
    }
    return prettyTime.format(tweet.getCreatedAt());
  }
}
