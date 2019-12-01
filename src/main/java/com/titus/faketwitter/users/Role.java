package com.titus.faketwitter.users;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class Role {

  @Id
  @GeneratedValue
  private Long id;
  
  private String role;
  
  @ElementCollection
  @CollectionTable(name = "role_privileges_mapping",
                   joinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")})
  private final Collection<Privilege> privileges = new HashSet<>();
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
  
  public Collection<Privilege> getPrivileges() {
    return privileges;
  }

  public void setPrivileges(Collection<Privilege> privileges) {
    this.privileges.clear();
    this.privileges.addAll(privileges);
  }
  
  public void addPrivileges(Privilege... privileges) {
    this.privileges.addAll(Arrays.asList(privileges));
  }

  public void removePrivileges(Privilege... privileges) {
    this.privileges.removeAll(Arrays.asList(privileges));
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
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Role)) {
      return false;
    }
    Role other = (Role) obj;
    return Objects.equals(id, other.id);
  }

  @Override
  public String toString() {
    return "Role [id=" + id + ", role=" + role + ", privileges=" + privileges + "]";
  }
}
