package com.titus.faketwitter.users;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class User extends AbstractUser {

  private String email;
  private String userName;
  private String firstName;
  private String lastName;
  private Status status;
  
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), 
             inverseJoinColumns = @JoinColumn(name = "role_id"))
  private final Collection<Role> roles = new HashSet<>();

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Collection<Role> getRoles() {
    return roles;
  }

  public void setRoles(Collection<Role> roles) {
    this.roles.clear();
    this.roles.addAll(roles);
  }
  
  public void addRoles(Role... roles) {
    this.roles.addAll(Arrays.asList(roles));
  }

  public void removeRoles(Role... roles) {
    this.roles.removeAll(Arrays.asList(roles));
  }
  
  public Collection<Privilege> getPrivileges() {
    return roles.stream().flatMap(r -> r.getPrivileges().stream()).collect(Collectors.toSet());
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
    if (!(obj instanceof User)) {
      return false;
    }
    User other = (User) obj;
    return Objects.equals(getId(), other.getId());
  }

  @Override
  public String toString() {
    return "User [id=" + getId() + ", email=" + email + ", userName=" + userName + ", firstName="
        + firstName + ", lastName=" + lastName + ", status=" + status + ", roles=" + roles
        + ", createdAt=" + getCreatedAt() + "]";
  }
}
