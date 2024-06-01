package com.cinema.sso.modal.user;

import org.springframework.stereotype.Service;

import com.cinema.sso.modal.user.dto.UserRequest;
import com.cinema.sso.modal.user.dto.UserResponse;
import com.cinema.sso.modal.user.entities.User;

@Service
public class UserMapper {
  // public User toUser(User user) {
  //   return User.builder()
  //       .id(user.id())
  //       .firstName(user.firstName())
  //       .lastName(user.lastName())
  //       .dateOfBirth(user.dateOfBirth())
  //       .email(user.email())
  //       // .password(user.password())
  //       .accountLocked(user.accountLocked())
  //       .enabled(user.enabled())
  //       .build();
  // }

  public UserResponse toUserResponse(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .dateOfBirth(user.getDateOfBirth())
        .email(user.getEmail())
        .build();
  }
}
