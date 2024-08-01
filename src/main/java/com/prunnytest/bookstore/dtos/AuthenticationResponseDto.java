package com.prunnytest.bookstore.dtos;

import lombok.Builder;

@Builder
public record AuthenticationResponseDto(
        String token
){}
