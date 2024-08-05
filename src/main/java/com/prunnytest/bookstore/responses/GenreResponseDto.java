package com.prunnytest.bookstore.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenreResponseDto {

    private Long id;

    private String name;

    private String description;
}
