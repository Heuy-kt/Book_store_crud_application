package com.prunnytest.bookstore.dtos;

import lombok.Builder;

@Builder
public record UserResponseDto(
        String firstName,
        String lastName,
        String username,
        String email

) {
}
