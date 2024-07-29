package com.prunnytest.bookstore.dtos;

import lombok.Data;
public record AuthorDto(
        String name,
        String Bio
){}
