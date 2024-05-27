package com.cinema.sso.modal.user.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
    // Integer id,

    @NotNull(message = "First name is not null")
    @NotEmpty(message = "First name is empty")
    String firstName,

    @NotNull(message = "Last name is not null")
    @NotEmpty(message = "Last name is empty")
    String lastName,

    @NotNull(message = "Date of birth is not null")
    @NotEmpty(message = "Date of birth is empty")
    LocalDate dateOfBirth,

    @NotNull(message = "Email is not null")
    @NotEmpty(message = "Email is empty")
    @Email(message = "Email is invalid")
    String email,

    @NotNull(message = "Password is not null")
    @NotEmpty(message = "Password is empty")
    String password,

    Boolean accountLocked,

    Boolean enabled
) {
}
