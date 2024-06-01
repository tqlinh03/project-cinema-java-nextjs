package com.cinema.sso.modal.user;

import java.security.Principal;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cinema.sso.modal.user.dto.ChangePasswordRequest;
import com.cinema.sso.modal.user.dto.UserRequest;
import com.cinema.sso.modal.user.dto.UserResponse;
import com.cinema.sso.modal.user.entities.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

  // public Integer create(UserRequest userRequest) {
  //   User user = userMapper.toUser(userRequest);
  //   System.out.printf("User: {}", user);
   
  //   // return userRepository.save(userRequest.toEntity()).getId();
  //   return 1;
  // }



  public void updateUserToken(String refreshToken, String email) {
    var user = userRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalStateException("User not found"));
    user.setRefreshToken(refreshToken);
    userRepository.save(user);
  }

  public String changePassword(ChangePasswordRequest changePasswordRequest, Principal connectedUser) {
   var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        // check if the current password is correct
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Sai mật khẩu");
        }
        // check if the two new passwords are the same
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmationPassword())) {
            throw new IllegalStateException("Mật khẩu mới không khớp");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));

        // save the new password
        repository.save(user);

        return "Đổi mật khẩu thành công";
  }
}
