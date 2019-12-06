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

  private static final int MAX_DISPLAY_URL_LENGTH = 23;

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
    String decoratedMessage = tweet.getMessage();
    if(decoratedMessage == null) {
      return "";
    }
    decoratedMessage = decorateHashtags(decoratedMessage);
    decoratedMessage = decorateUrls(decoratedMessage);
    
    
    return decoratedMessage;
  }

  private String decorateHashtags(String message) {
    Set<Hashtag> hashtags = tweet.getHashtags();
    
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

  private String decorateUrls(String message) {
    String[] split = message.split(" ");
    
    for(int i=0;i<split.length;i++) {
      if(split[i].startsWith("http")) {
        split[i] = encodeUrl(split[i]);
      }
    }
    
    return join(split);
  }
  
  private String encodeUrl(String url) {
    String anchor = "<a href=\"" + url + "\">";
    if(url.length() > MAX_DISPLAY_URL_LENGTH) {
      return anchor + url.substring(0, MAX_DISPLAY_URL_LENGTH) + "...</a>";  
    }
    return anchor + url + "</a>";
  }

  private String join(String[] split) {
    String toReturn = "";
    for (int i=0;i<split.length;i++) {
      toReturn += split[i] + " ";
    }
    return toReturn.substring(0, toReturn.length()-1);
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
