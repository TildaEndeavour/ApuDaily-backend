package com.example.ApuDaily.user.service;

import com.example.ApuDaily.user.dto.TimezoneResponseDto;
import java.util.List;

public interface TimezoneService {
  List<TimezoneResponseDto> getTimezones();
}
