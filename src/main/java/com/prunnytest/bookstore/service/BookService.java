package com.prunnytest.bookstore.service;

import com.prunnytest.bookstore.requests.BookDto;
import com.prunnytest.bookstore.responses.BookResponseDto;
import com.prunnytest.bookstore.exception.AlreadyExistsException;
import com.prunnytest.bookstore.exception.NotFoundException;

import java.util.List;

public interface BookService {

    BookResponseDto saveBook(BookDto bookDto) throws AlreadyExistsException, NotFoundException;

    BookResponseDto updateBook(Long id, BookDto bookDto);

    List<BookResponseDto> listAllBooks();

    BookResponseDto getBookByTitle(String title) throws NotFoundException;

    void deleteBook(Long id);



}
