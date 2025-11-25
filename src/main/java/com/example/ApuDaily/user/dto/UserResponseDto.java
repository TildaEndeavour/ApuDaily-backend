package com.example.ApuDaily.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
  private Long id;
  private String username;
  private String email;
  private UserStatusResponseDto status;
}
