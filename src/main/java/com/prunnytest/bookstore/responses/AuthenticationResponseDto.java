package com.prunnytest.bookstore.responses;

import lombok.Builder;

@Builder
public record AuthenticationResponseDto(
        String token
){}
