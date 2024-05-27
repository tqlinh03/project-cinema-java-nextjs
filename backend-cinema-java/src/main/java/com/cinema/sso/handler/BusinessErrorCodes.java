package com.cinema.sso.handler;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;

import lombok.Getter;

public enum BusinessErrorCodes {

  NO_CODE(0, NOT_IMPLEMENTED, "No code"),
  INCORRECT_CURRENT_PASSWORD(300, BAD_REQUEST, "mật khẩu hiện tại không đúng"),
  NEW_PASSWORD_DOES_NOT_MATCH(301, BAD_REQUEST, "Mật khẩu mới không khớp"),
  ACCOUNT_LOCKED(302, FORBIDDEN, "Tài khoản người dùng bị khóa"),
  ACCOUNT_DISABLED(303, FORBIDDEN, "Tài khoản người dùng chưa kích hoạt"),
  BAD_CREDENTIALS(304, FORBIDDEN, "Tên đăng nhập hoặc mật khẩu không chính xác"),
  ;
  
  @Getter
  private final int code;
  @Getter
  private final String description;
  @Getter
  private final HttpStatus httpStatus;

  BusinessErrorCodes(int code, HttpStatus status, String description) {
    this.code = code;
    this.description = description;
    this.httpStatus = status;

  }
}
