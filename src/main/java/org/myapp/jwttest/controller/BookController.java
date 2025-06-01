package org.myapp.jwttest.controller;

import lombok.extern.slf4j.Slf4j;
import org.myapp.jwttest.dto.BooksDTO;
import org.myapp.jwttest.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/book/")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBook(@PathVariable Long id) {
        BooksDTO book = bookService.getBook(id);
        return book != null
                ? ResponseEntity.status(HttpStatus.FOUND).body(book)
                : ResponseEntity.status(HttpStatus.NO_CONTENT).body("No book found");
    }

    @GetMapping("/")
    public ResponseEntity<List<BooksDTO>> getBooks() {
        List<BooksDTO> books = bookService.getAllBooks();


        if (books == null || books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(books);


    }

    @PostMapping("/")
    public ResponseEntity<BooksDTO> AddBook(@RequestBody BooksDTO bookDTO) {
        BooksDTO book = bookService.addBook(bookDTO);
        return book != null ? ResponseEntity.status(HttpStatus.CREATED).body(book) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(book);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BooksDTO> UpdateBook(@PathVariable("id") long id, @RequestBody BooksDTO bookDTO) {
        BooksDTO book = bookService.updateBook(id, bookDTO);
        return book != null ? ResponseEntity.status(HttpStatus.OK).body(book) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(book);
    }
}
