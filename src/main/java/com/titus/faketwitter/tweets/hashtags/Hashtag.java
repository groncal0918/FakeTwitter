package com.titus.faketwitter.tweets.hashtags;

import com.titus.faketwitter.tweets.Tweet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Hashtag {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;
  
  private String tag;
  
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "tweets_hashtags", joinColumns = @JoinColumn(name = "tag_id"), 
      inverseJoinColumns = @JoinColumn(name = "tweet_id"))
  private List<Tweet> tweets = new ArrayList<>();
  
  @CreationTimestamp 
  private Date createdAt;
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getTag() {
    return tag;
  }
  
  public void setTag(String tag) {
    this.tag = tag;
  }
  
  public List<Tweet> getTweets() {
    return tweets;
  }
  
  public void setTweets(List<Tweet> tweets) {
    this.tweets.clear();
    this.tweets.addAll(tweets);
  }
  
  public Date getCreatedAt() {
    return createdAt;
  }
  
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Hashtag)) {
      return false;
    }
    Hashtag other = (Hashtag)obj;
    return Objects.equals(id, other.id);
  }

  @Override
  public String toString() {
    return "Hashtag [id=" + id + ", tag=" + tag + ", tweets=" + tweets + ", createdAt=" + createdAt
      + "]";
  }
}
