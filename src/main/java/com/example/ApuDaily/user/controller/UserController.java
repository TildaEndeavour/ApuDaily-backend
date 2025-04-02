package com.example.ApuDaily.user.controller;

import com.example.ApuDaily.user.dto.SignupRequestDto;
import com.example.ApuDaily.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("${api.basePath}/${api.version}/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDto requestDto){
        userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
