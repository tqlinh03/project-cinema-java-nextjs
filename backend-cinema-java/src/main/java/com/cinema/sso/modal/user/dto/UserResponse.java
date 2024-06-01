package com.cinema.sso.modal.user.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
   private Integer id;
  private String firstName; 
  private String lastName;
  private LocalDate dateOfBirth;
  private String email; 
}
