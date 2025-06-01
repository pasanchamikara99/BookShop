package org.myapp.jwttest.controller;

import org.junit.jupiter.api.Test;
import org.myapp.jwttest.dto.BooksDTO;
import org.myapp.jwttest.service.BookService;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    private BookService bookService;

    @Test
    void getBook_Success() throws Exception {
        BookService bookService = mock(BookService.class);
        BookController bookController = new BookController(bookService);

        BooksDTO book = new BooksDTO();
        book.setId(1L);
        book.setName("Test Book");
        book.setAuthor("Author");

        when(bookService.getBook(1L)).thenReturn(book);

        // Act
        ResponseEntity<Object> response = bookController.getBook(1L);

        // Assert
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    void getBook_Fail() throws Exception {
        BookService bookService = mock(BookService.class);
        BookController bookController = new BookController(bookService);


        when(bookService.getBook(1L)).thenReturn(null);

        // Act
        ResponseEntity<Object> response = bookController.getBook(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("No book found", response.getBody());
    }

    @Test
    void getBooks_Success() {
        BookService bookService = mock(BookService.class);
        BookController bookController = new BookController(bookService);

        List<BooksDTO> booksList = new ArrayList<>();
        booksList.add(BooksDTO.builder()
                .id(1L)
                .name("Test Book 1")
                .price(2000)
                .author("Author 1")
                .build());

        booksList.add(BooksDTO.builder()
                .id(2L)
                .name("Test Book 2")
                .price(3000)
                .author("Author 2")
                .build());


        when(bookService.getAllBooks()).thenReturn(booksList);

        // Act
        ResponseEntity<List<BooksDTO>> response = bookController.getBooks();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(booksList.size(), response.getBody().size());
        assertEquals(booksList, response.getBody());
    }

    @Test
    void getBooks_Fail() throws Exception {
        BookService bookService = mock(BookService.class);
        BookController bookController = new BookController(bookService);


        when(bookService.getAllBooks()).thenReturn(null);

        // Act
        ResponseEntity<List<BooksDTO>> response = bookController.getBooks();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void addBook_Success() {
        BookService bookService = mock(BookService.class);
        BookController bookController = new BookController(bookService);

        BooksDTO book = new BooksDTO();
        book.setName("Test Book");
        book.setPrice(3000);
        book.setAuthor("Author");


        when(bookService.addBook(book)).thenReturn(book);

        ResponseEntity<BooksDTO> response = bookController.AddBook(book);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(book.getName(), response.getBody().getName());
        assertEquals(book.getPrice(), response.getBody().getPrice());

    }

    @Test
    void updateBook_Success() {

        BookService bookService = mock(BookService.class);
        BookController bookController = new BookController(bookService);

        BooksDTO book = new BooksDTO();
        book.setName("Test Book Edit");
        book.setPrice(3000);
        book.setAuthor("Author");

        when(bookService.updateBook(1L, book)).thenReturn(book);

        ResponseEntity<BooksDTO> response = bookController.UpdateBook(1L, book);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book.getName(), response.getBody().getName());
        assertEquals(book.getPrice(), response.getBody().getPrice());


    }
}