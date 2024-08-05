package com.prunnytest.bookstore.requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Setter;

@Builder

public record UserDtoLogin(
        @NotNull(message = "username cant be empty")
        String username,
        @NotBlank(message = "password cant be blank")
        String password
) {
}
