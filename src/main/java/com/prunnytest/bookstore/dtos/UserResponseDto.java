package com.prunnytest.bookstore.dtos;

import com.prunnytest.bookstore.model.enums.Roles;

public record UserResponseDto(
        String firstName,
        String lastName,
        String username,
        Roles role

) {
}
