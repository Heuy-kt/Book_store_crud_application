package com.prunnytest.bookstore.requests;

import com.prunnytest.bookstore.model.enums.Plan;
import lombok.Builder;
import lombok.Setter;


@Builder
public record BookDto(

        String title,
        String description,
        Long authorId,
        Long genreId,
        Plan plan
){}
