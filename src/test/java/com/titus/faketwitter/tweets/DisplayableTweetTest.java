package com.titus.faketwitter.tweets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.titus.faketwitter.tweets.hashtags.Hashtag;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class DisplayableTweetTest {

  @Test
  public void testHashtagEncoding() {
    assertEquals("", tweet(null).getMessage());
    
    assertEquals("A tweet with no hashtags", tweet("A tweet with no hashtags").getMessage());
    
    assertEquals("A tweet with one <a href=\"/tweets?tag=%23hashtag\">#hashtag</a>", 
                 tweet("A tweet with one #hashtag", hashtag(1L, "#hashtag")).getMessage());
    
    assertEquals("A tweet <a href=\"/tweets?tag=%23with\">#with</a> <a href=\"/tweets?tag=%23multiple\">#multiple</a> hashtag", 
                 tweet("A tweet #with #multiple hashtag", hashtag(1L, "#with"), hashtag(2L, "#multiple")).getMessage());
  }
  
  private DisplayableTweet tweet(String message, Hashtag...hashtags) {
    Tweet tweet = new Tweet();
    tweet.setMessage(message);
    tweet.setHashtags(Arrays.asList(hashtags));
    return new DisplayableTweet(tweet, null, null);
  }
  
  private Hashtag hashtag(long id, String tag) {
    Hashtag hashtag = new Hashtag();
    hashtag.setId(new Long(id));
    hashtag.setTag(tag);
    
    return hashtag;
  }
}
