package com.prunnytest.bookstore.requests;

import lombok.Builder;
import lombok.Setter;

@Builder
public record AuthorDto(
        String name,
        String Bio
){}
