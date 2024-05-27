package com.cinema.sso.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {
    @NotEmpty(message = "Họ không được để trống.")
    @NotNull(message = "Họ không được để trống.")
    private String firstName;

    @NotEmpty(message = "Tên không được để trống.")
    @NotNull(message = "Tên không được để trống.")
    private String lastName;

    @Email(message = "Email không đúng định dạng")
    @NotEmpty(message = "Email không được để trống")
    @NotNull(message = "Email không được để trống")
    private String email;

    @NotEmpty(message = "Mật khẩu không được để trống")
    @NotNull(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu phải dài tối thiểu 8 ký tự")
    private String password;
}