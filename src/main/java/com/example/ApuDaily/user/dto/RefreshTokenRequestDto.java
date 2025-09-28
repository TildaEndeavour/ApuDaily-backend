package com.example.ApuDaily.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequestDto {
    private String refreshToken;
}
