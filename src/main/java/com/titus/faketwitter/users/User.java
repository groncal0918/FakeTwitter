package com.titus.faketwitter.users;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User extends AbstractUser {

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
