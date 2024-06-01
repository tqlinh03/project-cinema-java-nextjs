package com.cinema.sso.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserDTO {
    private Integer _id;
    private String name;
    private String fullName;
    private String email;
}
