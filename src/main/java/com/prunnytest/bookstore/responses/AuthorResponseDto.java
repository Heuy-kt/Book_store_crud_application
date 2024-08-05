package com.prunnytest.bookstore.responses;

import com.prunnytest.bookstore.requests.BookDto;
import lombok.Builder;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
public class AuthorResponseDto {

    private Long id;

    private String name;

    private String Bio;

    private List<BookDto> books;

}
