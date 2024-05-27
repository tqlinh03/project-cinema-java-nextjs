package com.cinema.sso.modal.user;

import org.springframework.stereotype.Service;

import com.cinema.sso.modal.user.dto.UserRequest;
import com.cinema.sso.modal.user.entities.User;

@Service
public class UserMapper {
  public User toUser(UserRequest userRequest) {
    return User.builder()
        // .id(userRequest.id())
        .firstName(userRequest.firstName())
        .lastName(userRequest.lastName())
        .dateOfBirth(userRequest.dateOfBirth())
        .email(userRequest.email())
        .password(userRequest.password())
        .accountLocked(userRequest.accountLocked())
        .enabled(userRequest.enabled())
        .build();
  }
}
