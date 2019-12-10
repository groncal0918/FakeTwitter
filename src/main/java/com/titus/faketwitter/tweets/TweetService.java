package com.titus.faketwitter.tweets;

import com.titus.faketwitter.tweets.hashtags.Hashtag;
import com.titus.faketwitter.tweets.hashtags.HashtagRepository;
import com.titus.faketwitter.users.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.ocpsoft.prettytime.PrettyTime;
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

  public List<DisplayableTweet> findAll() {
    List<Tweet> tweets = tweetRepository.findAllByOrderByCreatedAtDesc();
    return display(tweets);
  }

  public List<DisplayableTweet> findAllByUser(User user) {
    List<Tweet> tweets = tweetRepository.findAllByUserOrderByCreatedAtDesc(user);
    return display(tweets);
  }

  public List<DisplayableTweet> findAllByUsers(Collection<User> users) {
    return display(tweetRepository.findAllByUserInOrderByCreatedAtDesc(users));
  }

  public List<DisplayableTweet> findAllWithTag(String tag) {
    Hashtag hashtag = hashtagRepository.findByTag(tag);
    if(hashtag == null) {
      return Arrays.asList();
    }
    return display(tweetRepository.findAllByHashtagsContainsOrderByCreatedAtDesc(hashtag));
  }

  private List<DisplayableTweet> display(List<Tweet> tweets) {
    PrettyTime prettyTime = new PrettyTime();
    Date now = new Date();
    return tweets.stream().map(tweet -> new DisplayableTweet(tweet, prettyTime, now)).collect(Collectors.toList());
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
