package com.example.ApuDaily.user.controller;

import com.example.ApuDaily.user.dto.SignupRequestDto;
import com.example.ApuDaily.user.dto.UserResponseDto;
import com.example.ApuDaily.user.service.AuthUtil;
import com.example.ApuDaily.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("${api.basePath}/${api.version}/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AuthUtil authUtil;

    @PostMapping
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDto requestDto){
        userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<UserResponseDto> getUserDetails() {
        Long authUserId = authUtil.getUserIdFromAuthentication(
                SecurityContextHolder.getContext().getAuthentication());
        UserResponseDto responseDto = userService.getUserDetailsById(authUserId);
        return ResponseEntity.ok(responseDto);
    }
}
