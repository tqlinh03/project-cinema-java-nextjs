package com.cinema.sso.modal.user;

import org.springframework.stereotype.Service;

import com.cinema.sso.modal.user.dto.UserRequest;
import com.cinema.sso.modal.user.entities.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public Integer create(UserRequest userRequest) {
    User user = userMapper.toUser(userRequest);
    System.out.printf("User: {}", user);
   
    // return userRepository.save(userRequest.toEntity()).getId();
    return 1;
  }

  public void updateUserToken(String refreshToken, String email) {
    var user = userRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalStateException("User not found"));
    user.setRefreshToken(refreshToken);
    userRepository.save(user);
  }
}
