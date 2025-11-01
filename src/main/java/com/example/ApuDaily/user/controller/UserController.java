package com.example.ApuDaily.user.controller;

import com.example.ApuDaily.user.dto.SignupRequestDto;
import com.example.ApuDaily.user.dto.TimezoneResponseDto;
import com.example.ApuDaily.user.dto.UserProfileResponseDto;
import com.example.ApuDaily.user.dto.UserResponseDto;
import com.example.ApuDaily.user.service.AuthUtil;
import com.example.ApuDaily.user.service.TimezoneService;
import com.example.ApuDaily.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("${api.basePath}/${api.version}/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    TimezoneService timezoneService;

    @Autowired
    AuthUtil authUtil;

    @PostMapping
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDto requestDto){
        userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<UserResponseDto> getUserDetails() {
        Long authUserId = authUtil.getUserIdFromAuthentication(
                SecurityContextHolder.getContext().getAuthentication());
        UserResponseDto responseDto = userService.getUserDetailsById(authUserId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/profiles")
    public ResponseEntity<List<UserProfileResponseDto>> getAllUsersProfiles() {
        List<UserProfileResponseDto> profiles = userService.getAllUserProfiles();
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/profiles/{user_id}")
    public ResponseEntity<UserProfileResponseDto> getUserProfileById(@PathVariable("user_id") long userId){
        UserProfileResponseDto profile = userService.getUserProfileById(userId);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/timezones")
    public ResponseEntity<List<TimezoneResponseDto>> getTimezones() {
        List<TimezoneResponseDto> responseDtoList = timezoneService.getTimezones();
        return ResponseEntity.ok(responseDtoList);
    }
}
