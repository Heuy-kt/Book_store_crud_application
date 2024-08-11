package com.prunnytest.bookstore.controller;

import com.prunnytest.bookstore.model.User;
import com.prunnytest.bookstore.model.enums.Roles;
import com.prunnytest.bookstore.requests.BookDto;
import com.prunnytest.bookstore.responses.BookResponseDto;
import com.prunnytest.bookstore.exception.AlreadyExistsException;
import com.prunnytest.bookstore.exception.NotFoundException;
import com.prunnytest.bookstore.service.Impl.BookServiceImpl;
import com.prunnytest.bookstore.service.Impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.prunnytest.bookstore.util.Constants.*;

@Tag(name = "Book", description = "Book set of APIs")
@RestController
@RequestMapping("/api/v1/book")
@Tag(name = "books")
@RequiredArgsConstructor
public class BookController {


    private BookServiceImpl bookService;
    private UserServiceImpl userService;


    @Operation(
            description = "registering a book",
            summary = "Register book using the Author and Genre reference ID's",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "NOT FOUND",
                            responseCode = "404"
                    )
            }
    )
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> registerBook(@RequestBody BookDto bookDto) throws AlreadyExistsException, NotFoundException {

        BookResponseDto saveBook = bookService.saveBook(bookDto);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.CREATED.value());
        response.put("message", BOOK_CREATED_SUCCESS);
        response.put("data", saveBook);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @Operation(summary = "Retrieve the Book using Book Title - Author and Genre of the book also comes as a response")
    @GetMapping("/{title}")
    ResponseEntity<Map<String, Object>> getBookByTitle(@PathVariable("title") String title)  throws NotFoundException {

        BookResponseDto book = bookService.getBookByTitle(title);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", BOOK_RETRIEVED_SUCCESS);
        response.put("data", book);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update the Book using the book ID")
    @PutMapping("management/{id}")
    public ResponseEntity<Map<String, Object>> updateBookDetails(@PathVariable("id") Long id, @RequestBody BookDto bookDto) {
        BookResponseDto updateBook = bookService.updateBook(id, bookDto);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", BOOK_UPDATED_SUCCESS);
        response.put("data", updateBook);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Delete the Book using the book ID")
    @DeleteMapping("management/{id}")
    public ResponseEntity<Map<String, Object>> deleteBooks(@PathVariable("id") Long id) {

        bookService.deleteBook(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.NO_CONTENT.value());
        response.put("message", BOOK_DELETED_SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

    }

    @Operation(summary = "List all the books in catalogue with authors and genre details")
    @GetMapping("basic/listBooks")
    public ResponseEntity<Map<String, Object>> getAllBooks() {
        List<BookResponseDto> bookList = bookService.listAllBooks();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", BOOK_RETRIEVED_SUCCESS);
        response.put("data", bookList);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "testing premium collections")
    @GetMapping("premium/premium_books")
    public ResponseEntity<List<String>> getPremiumBooks(){
        return ResponseEntity.ok().body(
                List.of("PREMIUM NEW KINGS",
                        "PREMIUM GREEN",
                        "PREMIUM BLACK")
        );
    }

    @Operation(summary = "upgrading to premium")
    @PutMapping("management/upgrade")
    public ResponseEntity<String> upgradeBook(@RequestParam("username")String name){
        User user = userService.getUserByUsername(name);
        user.setRole(Roles.PREMIUM);

        return ResponseEntity.ok().body("USER upgraded");

    }

    @Operation(summary = "upgrading to premium")
    @GetMapping("admin/testing_admin")
    public ResponseEntity<String> testAdmin(){
        return ResponseEntity.ok().body("admin tested");
    }

    @Operation(summary = "upgrading to premium")
    @GetMapping("management/testing_management")
    public ResponseEntity<String> testManagement(){
        return ResponseEntity.ok().body("testing management");
    }

}
