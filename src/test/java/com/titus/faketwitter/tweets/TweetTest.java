package com.titus.faketwitter.tweets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.titus.faketwitter.tweets.hashtags.Hashtag;

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
  
  @Test
  public void testDisplayMessage() {
    Tweet tweetWithNoMessage = new Tweet();
    assertEquals("", tweetWithNoMessage.getDisplayMessage());
    
    Tweet tweetWithNoHashtags = new Tweet();
    tweetWithNoHashtags.setMessage("A tweet with no hashtags");
    assertEquals("A tweet with no hashtags", tweetWithNoHashtags.getDisplayMessage());
    
    Tweet tweetWithOneHashtag = new Tweet();
    tweetWithOneHashtag.setMessage("A tweet with one #hashtag");
    Hashtag hashtagHashtag = new Hashtag();
    hashtagHashtag.setTag("#hashtag");
    tweetWithOneHashtag.setHashtags(Arrays.asList(hashtagHashtag));
    assertEquals("A tweet with one <a href=\"/tweets?tag=%23hashtag\">#hashtag</a>", tweetWithOneHashtag.getDisplayMessage());
    
    Tweet tweetWithMultipleHashtags = new Tweet();
    tweetWithMultipleHashtags.setMessage("A tweet #with #multiple hashtag");
    Hashtag hashtagWith = new Hashtag();
    hashtagWith.setId(new Long(1L));
    hashtagWith.setTag("#with");
    Hashtag hashtagMultiple = new Hashtag();
    hashtagWith.setId(new Long(2L));
    hashtagMultiple.setTag("#multiple");
    tweetWithMultipleHashtags.setHashtags(Arrays.asList(hashtagWith, hashtagMultiple));
    assertEquals("A tweet <a href=\"/tweets?tag=%23with\">#with</a> <a href=\"/tweets?tag=%23multiple\">#multiple</a> hashtag", tweetWithMultipleHashtags.getDisplayMessage());
  }
  
}
