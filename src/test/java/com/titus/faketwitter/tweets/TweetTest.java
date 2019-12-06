package com.titus.faketwitter.tweets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class TweetTest {

  @Test
  public void testFindTagsInMessage() {
    Tweet tweetWithNoMessage = new Tweet();
    assertEquals(Arrays.asList(), tweetWithNoMessage.findTags());
    
    Tweet tweetWithNoHashtags = new Tweet();
    tweetWithNoHashtags.setMessage("A tweet with no hashtags");
    assertEquals(Arrays.asList(), tweetWithNoHashtags.findTags());
    
    Tweet tweetWithOneHashtag = new Tweet();
    tweetWithOneHashtag.setMessage("A tweet with one #hashtag");
    assertEquals(Arrays.asList("#hashtag"), tweetWithOneHashtag.findTags());
    
    Tweet tweetWithMutipleHashtags = new Tweet();
    tweetWithMutipleHashtags.setMessage("A tweet with #multiple #hashtags");
    assertEquals(Arrays.asList("#multiple", "#hashtags"), tweetWithMutipleHashtags.findTags());
    
    Tweet tweetWithHashtagsWithMutipleWords = new Tweet();
    tweetWithHashtagsWithMutipleWords.setMessage("A tweet with #hashtags with #multiple words");
    assertEquals(Arrays.asList("#hashtags", "#multiple"), tweetWithHashtagsWithMutipleWords.findTags());
  }
}
