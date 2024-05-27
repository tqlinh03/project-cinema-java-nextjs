package com.cinema.sso.modal.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinema.sso.modal.user.dto.UserRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  
  @PostMapping
  public ResponseEntity<Integer> create(
    @RequestBody  @Valid UserRequest userRequest
  ) {
    return ResponseEntity.ok(userService.create(userRequest));
  }
}
