package com.prunnytest.bookstore.service.Impl;

import com.prunnytest.bookstore.requests.AuthorDto;
import com.prunnytest.bookstore.responses.AuthorResponseDto;
import com.prunnytest.bookstore.requests.BookDto;
import com.prunnytest.bookstore.responses.BookResponseDto;
import com.prunnytest.bookstore.responses.GenreResponseDto;
import com.prunnytest.bookstore.exception.AlreadyExistsException;
import com.prunnytest.bookstore.exception.NotFoundException;
import com.prunnytest.bookstore.model.Author;
import com.prunnytest.bookstore.model.Book;
import com.prunnytest.bookstore.model.Genre;
import com.prunnytest.bookstore.repository.AuthorRepository;
import com.prunnytest.bookstore.repository.BookRepository;
import com.prunnytest.bookstore.repository.GenreRepository;
import com.prunnytest.bookstore.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    ModelMapper modelMapper = new ModelMapper();


    @Override
    public BookResponseDto saveBook(BookDto bookDto) throws AlreadyExistsException, NotFoundException {

        Optional<Book> optionalBook = bookRepository.findBookByTitle(bookDto.title());
        Optional<Author> optionalAuthor = authorRepository.findById(bookDto.authorId());
        Optional<Genre> optionalGenre = genreRepository.findById(bookDto.genreId());

        if (optionalBook.isPresent() ){
            throw new AlreadyExistsException("Book already exists");
        }

        if (optionalAuthor.isEmpty() || !checkAuthor(optionalAuthor.get().getId())){
            throw new NotFoundException("Author not found");
        }

        if (optionalGenre.isEmpty()||!checkGenre(optionalAuthor.get().getId())){
            throw new NotFoundException("Genre not found");
        }

        Book book = new Book();
        book.setTitle(bookDto.title());
        book.setDescription(bookDto.description());
        book.setAuthor(optionalAuthor.get());
        book.setGenre(optionalGenre.get());

        bookRepository.save(book);

       return convertToBookResponseDto(book);

    }

    private BookResponseDto convertToBookResponseDto(Book book) {
        return BookResponseDto
                .builder()
                .title(book.getTitle())
                .description(book.getDescription())
                .authorResponseDto(modelMapper.map(book.getAuthor(), AuthorResponseDto.class))
                .build();
    }

    @Override
    public BookResponseDto updateBook(Long id, BookDto bookDto) {

        Optional<Book> optionalBook = bookRepository.findById(id);
        Optional<Author> optionalAuthor = authorRepository.findById(bookDto.authorId());
        Optional<Genre> optionalGenre = genreRepository.findById(bookDto.genreId());

        if (optionalBook.isEmpty()){
            throw new RuntimeException("Book not found");
        }

        if (optionalAuthor.isEmpty()){
            throw new RuntimeException("Author not found");
        }

        if (optionalGenre.isEmpty()){
            throw new RuntimeException("Genre not found");
        }

        Book book = optionalBook.get();
        book.setTitle(bookDto.title());
        book.setDescription(bookDto.description());
        book.setAuthor(optionalAuthor.get());
        book.setGenre(optionalGenre.get());

        bookRepository.save(book);

        return convertToBookResponseDto(book);
    }

    @Override
    public List<BookResponseDto> listAllBooks() {

        List<Book> books = (List<Book>) bookRepository.findAll();

        return books.stream()
                .map(this::convertToBookResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDto getBookByTitle(String title) throws NotFoundException {

        Book book = bookRepository.findBookByTitle(title)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        return convertToBookResponseDto(book);

    }

    public Book getOriginalBookByTitle(String title) throws NotFoundException {

        Book book = bookRepository.findBookByTitle(title)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        return book;

    }

    @Override
    public void deleteBook(Long id) {

        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isEmpty()){
            throw new RuntimeException("Book not found");
        }

        Book book = optionalBook.get();

        bookRepository.delete(book);

    }

    public boolean checkAuthor(long id){
        return authorRepository.existsById(id);
    }
    public boolean checkGenre(long id){
        return genreRepository.existsById(id);
    }

}
