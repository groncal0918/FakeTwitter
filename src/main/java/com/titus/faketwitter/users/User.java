package com.titus.faketwitter.users;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User extends AbstractUser {

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_follows", joinColumns = @JoinColumn(name = "user_id"), 
      inverseJoinColumns = @JoinColumn(name = "followed_user_id"))
  private Set<User> follows = new HashSet<>();
  
  public void setFollows(Set<User> follows) {
    this.follows.clear();
    this.follows.addAll(follows);
  }
  
  public Set<User> getFollows() {
    return follows;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Objects.hash(this.getId());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (!(obj instanceof User)) {
      return false;
    }
    User other = (User)obj;
    return Objects.equals(this.getId(), other.getId());
  }

  @Override
  public String toString() {
    return "User [getId()=" + getId() + ", getEmail()=" + getEmail()
      + ", getUsername()=" + getUsername() + ", getFirstName()=" + getFirstName()
      + ", getLastName()=" + getLastName() + ", getActive()=" + getActive() + ", getCreatedAt()="
      + getCreatedAt() + ", getRoles()=" + getRoles() + "]";
  }
}
