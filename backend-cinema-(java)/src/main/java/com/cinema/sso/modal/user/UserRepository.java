package com.cinema.sso.modal.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinema.sso.modal.user.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmail(String email);
  // Optional<User> updateUserToken(String refreshTokeb, Integer id);
}
