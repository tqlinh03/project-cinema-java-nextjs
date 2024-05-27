package com.cinema.sso.modal.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinema.sso.modal.role.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
  Optional<Role> findByName(String role);
}
