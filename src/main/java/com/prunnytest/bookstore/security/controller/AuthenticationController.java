package com.prunnytest.bookstore.security.controller;

import com.prunnytest.bookstore.responses.AuthenticationResponseDto;
import com.prunnytest.bookstore.requests.UserDto;
import com.prunnytest.bookstore.requests.UserDtoLogin;
import com.prunnytest.bookstore.security.services.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("register")
    public ResponseEntity<AuthenticationResponseDto> register(@Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok(authenticationService.register(userDto));
    }

    @GetMapping("login")
    public ResponseEntity<AuthenticationResponseDto> login(@Valid @RequestBody UserDtoLogin userDto){
        return ResponseEntity.ok(authenticationService.authenticate(userDto));
    }

}
