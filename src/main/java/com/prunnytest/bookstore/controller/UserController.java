package com.prunnytest.bookstore.controller;

import com.prunnytest.bookstore.dtos.UserDto;
import com.prunnytest.bookstore.dtos.UserResponseDto;

import com.prunnytest.bookstore.service.Impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("v1/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("create")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.saveUser(userDto));

    }
}
