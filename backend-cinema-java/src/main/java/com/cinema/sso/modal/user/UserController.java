package com.cinema.sso.modal.user;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinema.sso.modal.user.dto.ChangePasswordRequest;
import com.cinema.sso.modal.user.dto.UserRequest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {

  private final UserService userService;
  
  @PostMapping
  // public ResponseEntity<Integer> create(
  //   @RequestBody  @Valid UserRequest userRequest
  // ) {
  //   return ResponseEntity.ok(userService.create(userRequest));
  // }

  @PatchMapping("/change-password")
  public ResponseEntity<?> changePassword(
    @RequestBody @Valid ChangePasswordRequest changePasswordRequest,
    Principal connectedUser
  ) {
    
    return ResponseEntity.ok(userService.changePassword(changePasswordRequest, connectedUser));
  }
}
