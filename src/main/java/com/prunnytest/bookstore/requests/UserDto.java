package com.prunnytest.bookstore.requests;

import com.prunnytest.bookstore.model.enums.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Setter;


@Builder
public record UserDto(
        @NotNull(message = "first name cant be blank")
        String firstName,
        String lastName,
        @NotNull(message = "username cant be blank")
        String userName,
        @Email(message = "incorrect email")
        String email,
        @NotBlank(message = "password cant be blank")
        String password,
        @NotBlank(message = "hello, check role")
        Roles role
) {
}
