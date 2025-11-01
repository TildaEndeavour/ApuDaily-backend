package com.example.ApuDaily.user.service;

import com.example.ApuDaily.exception.ApiException;
import com.example.ApuDaily.exception.ErrorMessage;
import com.example.ApuDaily.user.dto.TimezoneResponseDto;
import com.example.ApuDaily.user.model.Timezone;
import com.example.ApuDaily.user.repository.TimezoneRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TimezoneServiceImpl implements TimezoneService {

    @Autowired
    TimezoneRepository timezoneRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<TimezoneResponseDto> getTimezones() {
        List<Timezone> timezones = timezoneRepository.findAll();
        if (timezones.isEmpty()) throw new ApiException(ErrorMessage.NOT_FOUND, null, HttpStatus.NOT_FOUND);

        return timezones.stream()
                .map(timezone -> modelMapper.map(timezone, TimezoneResponseDto.class))
                .sorted(Comparator.comparing(TimezoneResponseDto::getId))
                .toList();
    }
}
