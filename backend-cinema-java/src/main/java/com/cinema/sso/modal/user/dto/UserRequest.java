package com.cinema.sso.modal.user.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
    // Integer id,

    @NotNull(message = "Họ không được để trống")
    @NotEmpty(message = "Họ không được để trống")
    String firstName,

    @NotNull(message = "Tên không được để trống")
    @NotEmpty(message = "Tên không được để trống")
    String lastName,

    @NotNull(message = "Ngày sinh không được để trống")
    @NotEmpty(message = "Ngày sinh không được để trống")
    LocalDate dateOfBirth,

    @NotNull(message = "Email không được để trống")
    @NotEmpty(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    String email,

    @NotNull(message = "Password is not null")
    @NotEmpty(message = "Password is empty")
    String password,

    Boolean accountLocked,

    Boolean enabled
) {
}
