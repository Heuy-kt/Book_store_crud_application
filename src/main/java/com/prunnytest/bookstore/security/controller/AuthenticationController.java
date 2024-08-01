package com.prunnytest.bookstore.security.controller;

import com.prunnytest.bookstore.dtos.AuthenticationResponseDto;
import com.prunnytest.bookstore.dtos.UserDto;
import com.prunnytest.bookstore.security.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody UserDto userDto){
        return ResponseEntity.ok(authenticationService.register(userDto));
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody UserDto userDto){
        return ResponseEntity.ok(authenticationService.authenticate(userDto));
    }

}
