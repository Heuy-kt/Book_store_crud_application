package com.prunnytest.bookstore.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDto(
        @NotNull(message = "first name cant be blank")
        String firstName,
        String lastName,
        @NotNull(message = "username cant be blank")
        String userName,
        @Email(message = "incorrect email")
        String email,
        @NotBlank(message = "password cant be blank")
        String password
) {
}
