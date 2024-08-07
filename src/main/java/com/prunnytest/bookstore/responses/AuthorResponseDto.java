package com.prunnytest.bookstore.responses;

import com.prunnytest.bookstore.requests.BookDto;
import lombok.Builder;
import lombok.Setter;

import java.util.List;

@Builder
public record AuthorResponseDto(
        Long id,
        String name,
        String Bio,
        List<BookDto> books
){

}
