package com.titus.faketwitter.tweets;

import com.titus.faketwitter.tweets.hashtags.Hashtag;
import com.titus.faketwitter.users.User;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Tweet {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;
    
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user;
    
  private String message;
  
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "tweets_hashtags", joinColumns = @JoinColumn(name = "tweet_id"), 
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<Hashtag> hashtags = new HashSet<>();
  
  @CreationTimestamp 
  private Date createdAt;

  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
  
  public User getUser() {
    return user;
  }
  
  public void setUser(User user) {
    this.user = user;
  }
  
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
  
  public Date getCreatedAt() {
    return createdAt;
  }
  
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }
  
  public Set<Hashtag> getHashtags() {
    return hashtags;
  }
  
  public void setHashtags(Collection<Hashtag> hashtags) {
    this.hashtags.clear();
    this.hashtags.addAll(hashtags);
  }

  public List<String> findTags() {
    List<String> tags = new ArrayList<>();
    if(this.message == null) {
      return tags;
    }
    String[] messageSplitByHash = this.message.split("#");
    for(int i=1;i<messageSplitByHash.length;i++) {
      String tag = "#" + messageSplitByHash[i].trim();
      if(tag.contains(" ")) {
        tags.add(tag.substring(0, tag.indexOf(" ")));
      } else {
        tags.add(tag);
      }
    }
    return tags;
  }
  
  public String getDisplayMessage() {
    if(message == null) {
      return "";
    }
    if(hashtags.isEmpty()) {
      return message;
    }
    String decoratedMessage = this.message;
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
  
  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Tweet)) {
      return false;
    }
    Tweet other = (Tweet)obj;
    return Objects.equals(id, other.id);
  }

  @Override
  public String toString() {
    return "Tweet [id=" + id + ", user=" + user + ", message=" + message + ", createdAt="
      + createdAt + "]";
  }
}
