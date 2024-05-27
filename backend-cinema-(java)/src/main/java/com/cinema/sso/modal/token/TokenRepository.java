package com.cinema.sso.modal.token;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinema.sso.modal.token.entities.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {
  Optional<Token> findByToken(String token);
}
