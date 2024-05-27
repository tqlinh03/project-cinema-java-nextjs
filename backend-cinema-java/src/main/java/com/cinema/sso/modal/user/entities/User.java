package com.cinema.sso.modal.user.entities;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cinema.sso.modal.role.entities.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_user")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails, Principal {

  @Id
  @GeneratedValue
  private Integer id;
  private String firstName; 
  private String lastName;
  private LocalDate dateOfBirth;
  @Column(unique = true)
  private String email; 
  private String password;
  private Boolean accountLocked;
  private Boolean enabled;
  private String refreshToken;
  // @ManyToMany(fetch = FetchType.EAGER, mappedBy = "user")
  @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

  @CreatedDate
  @Column(nullable = false ,updatable = false)
  private LocalDateTime createDate;

  @LastModifiedDate
  @Column(insertable = false)
  private LocalDateTime lastModifiedDate;
 
  @Override
  public String getName() {
    return lastName;
  }

  @Override
  public String getPassword() {
    return password;
  }

  
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles
            .stream()
            .map(r -> new SimpleGrantedAuthority(r.getName()))
            .collect(Collectors.toList());
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !accountLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  public String getFullName() {
    return firstName + " " + lastName;
}

}
