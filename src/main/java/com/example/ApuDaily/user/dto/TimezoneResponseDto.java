package com.example.ApuDaily.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimezoneResponseDto {
    private Long id;
    private String name;
    private String GMT;
}
