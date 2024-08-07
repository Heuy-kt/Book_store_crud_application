package com.prunnytest.bookstore.requests;

import lombok.Builder;


@Builder
public record BookDto(

        String title,
        String description,
        Long authorId,
        Long genreId
){}
