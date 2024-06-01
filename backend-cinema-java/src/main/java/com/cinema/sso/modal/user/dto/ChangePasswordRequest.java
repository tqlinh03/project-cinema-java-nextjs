package com.cinema.sso.modal.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ChangePasswordRequest {
    @NotNull(message ="Mật khẩu không được để trống")
    @NotEmpty(message ="Mật khẩu không được để trống")
    private String currentPassword;
    @NotNull(message ="Mật khẩu mới không được để trống")
    @NotEmpty(message ="Mật khẩu mới không được để trống")
    private String newPassword;
    @NotNull(message ="Xác nhận mật khẩu không được để trống")
    @NotEmpty(message ="Xác nhận mật khẩu không được để trống")
    private String confirmationPassword;
}
