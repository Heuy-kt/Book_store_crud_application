package com.prunnytest.bookstore.responses;

import lombok.Builder;

@Builder
public record UserResponseDto(
        String firstName,
        String lastName,
        String username,
        String email

) {
}
