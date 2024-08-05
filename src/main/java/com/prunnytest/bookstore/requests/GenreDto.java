package com.prunnytest.bookstore.requests;


import lombok.Builder;
import lombok.Setter;


@Builder
public record GenreDto(

    String name,

    String description){
}
