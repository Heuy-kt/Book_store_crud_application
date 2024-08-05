package com.prunnytest.bookstore.responses;

import lombok.Builder;

@Builder
public record BookResponseDto(
     String title,

     String description,

     AuthorResponseDto authorResponseDto,

     GenreResponseDto genreResponseDto
){}
