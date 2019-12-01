package com.titus.faketwitter.users;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserAccount extends AbstractUser {

  private String userName;
  private String password;
  private boolean enabled;
  
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public boolean isEnabled() {
    return enabled;
  }
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof UserAccount)) {
      return false;
    }
    UserAccount other = (UserAccount) obj;
    return Objects.equals(getId(), other.getId());
  }
  
  @Override
  public String toString() {
    return "UserAccount [userName=" + userName + ", enabled=" + enabled + ", getId()=" + getId()
        + ", getCreatedAt()=" + getCreatedAt() + "]";
  }
}
