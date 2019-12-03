package com.titus.faketwitter.tweets;

import com.titus.faketwitter.tweets.hashtags.Hashtag;
import com.titus.faketwitter.tweets.hashtags.HashtagRepository;
import com.titus.faketwitter.users.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TweetService {

  private final TweetRepository tweetRepository;

  private final HashtagRepository hashtagRepository;

  @Autowired
  public TweetService(TweetRepository tweetRepository, HashtagRepository hashtagRepository) {
    this.tweetRepository = tweetRepository;
    this.hashtagRepository = hashtagRepository;
  }

  public List<Tweet> findAll() {
    List<Tweet> tweets = tweetRepository.findAllByOrderByCreatedAtDesc();
    return tweets;
  }

  public List<Tweet> findAllByUser(User user) {
    List<Tweet> tweets = tweetRepository.findAllByUserOrderByCreatedAtDesc(user);
    return tweets;
  }

  public List<Tweet> findAllByUsers(List<User> users) {
    List<Tweet> tweets = tweetRepository.findAllByUserInOrderByCreatedAtDesc(users);
    return tweets;
  }

  public List<Tweet> findAllWithTag(String tag) {
    Hashtag hashtag = hashtagRepository.findByTag(tag);
    if(hashtag == null) {
      return Arrays.asList();
    }
    return tweetRepository.findAllByHashtagsContainsOrderByCreatedAtDesc(hashtag);
  }
  
  public void save(Tweet tweet) {
    List<String> tags = tweet.findTags();
    List<Hashtag> hashtags = new ArrayList<>();
    for (String tag : tags) {
      Hashtag hashtag = hashtagRepository.findByTag(tag);
      if(hashtag == null) {
        hashtag = new Hashtag();
        hashtag.setTag(tag);
        hashtag = hashtagRepository.save(hashtag);
      }
      hashtags.add(hashtag);
    }
    tweet.setHashtags(hashtags);
    tweetRepository.save(tweet);
  }  
}
