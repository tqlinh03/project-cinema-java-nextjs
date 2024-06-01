package com.cinema.sso.modal.role.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.cinema.sso.modal.user.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue
    private Integer _id;
    @Column(unique = true)
    private String name;
    private String description;
    private boolean isActive;
    // @ManyToMany(mappedBy = "roles")
    // @JsonIgnore
    // private List<User> user;

    // @CreatedDate
    // @Column(nullable = false, updatable = false)
    // private LocalDateTime createdDate;

    // @LastModifiedDate
    // @Column(insertable = false)
    // private LocalDateTime lastModifiedDate;
}
