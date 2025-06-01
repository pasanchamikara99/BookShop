package org.myapp.jwttest.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.myapp.jwttest.dto.BooksDTO;
import org.myapp.jwttest.models.Book;
import org.myapp.jwttest.repository.BookRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//@SpringBootTest
@AutoConfigureMockMvc
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getAllBooks() {

        BookServiceImpl bookService = new BookServiceImpl(bookRepository, modelMapper);

        List<Book> booksList = new ArrayList<>();
        booksList.add(Book.builder()
                .id(1L)
                .name("Test Book 1")
                .price(2000)
                .author("Author 1")
                .build());

        booksList.add(Book.builder()
                .id(2L)
                .name("Test Book 2")
                .price(3000)
                .author("Author 2")
                .build());


        BooksDTO dto1 = BooksDTO.builder()
                .id(1L)
                .name("Test Book 1")
                .price(2000)
                .author("Author 1")
                .build();

        BooksDTO dto2 = BooksDTO.builder()
                .id(2L)
                .name("Test Book 2")
                .price(3000)
                .author("Author 2")
                .build();

        when(bookRepository.findAll()).thenReturn(booksList);

        // Mock modelMapper.map(book, BooksDTO.class) for each book
        when(modelMapper.map(booksList.get(0), BooksDTO.class)).thenReturn(dto1);
        when(modelMapper.map(booksList.get(1), BooksDTO.class)).thenReturn(dto2);

        // Act
        List<BooksDTO> result = bookService.getAllBooks();


        assertNotNull(result);
        assertEquals(2, result.size());

    }

    @Test
    void addBook() {
        BookServiceImpl bookService = new BookServiceImpl(bookRepository, modelMapper);

        Book book = Book.builder()
                .name("Test Book")
                .price(2000)
                .author("Author")
                .build();

        BooksDTO booksDTO = BooksDTO.builder()
                .name("Test Book")
                .price(2000)
                .author("Author")
                .build();

        when(modelMapper.map(booksDTO, Book.class)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(modelMapper.map(book, BooksDTO.class)).thenReturn(booksDTO); // this was missing

        BooksDTO result = bookService.addBook(booksDTO);

        assertNotNull(result);
        assertEquals(book.getName(), result.getName());
    }

    @Test
    void getBook_WhenBookExists_ReturnsMappedDTO() {

        BookServiceImpl bookService = new BookServiceImpl(bookRepository, modelMapper);

        // Arrange
        Long id = 1L;
        Book book = new Book();
        book.setId(id);
        book.setName("Test Book");

        BooksDTO booksDTO = BooksDTO.builder()
                .id(id)
                .name("Test Book")
                .build();

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(modelMapper.map(book, BooksDTO.class)).thenReturn(booksDTO);

        // Act
        BooksDTO result = bookService.getBook(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Test Book", result.getName());
    }

    @Test
    void getBook_WhenBookNotExists() {

        BookServiceImpl bookService = new BookServiceImpl(bookRepository, modelMapper);

        Book book = null;

        // Arrange
        Long id = 1L;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        BooksDTO result = bookService.getBook(id);

        // Assert
        assertNull(result);
    }

    @Test
    void getBooks_WhenBookNotExists() {

        BookServiceImpl bookService = new BookServiceImpl(bookRepository, modelMapper);

        // Arrange
        List<Book> book = new ArrayList<>();

        when(bookRepository.findAll()).thenReturn(book);

        // Act
        List<BooksDTO> result = bookService.getAllBooks();

        // Assert
        assertNull(result);
    }


    @Test
    void updateBook() {
        BookServiceImpl bookService = new BookServiceImpl(bookRepository, modelMapper);

        //Arrange
        Long id = 1L;

        BooksDTO booksDTO = BooksDTO.builder()
                .id(id)
                .name("Test Book")
                .price(2000)
                .build();

        Book book = Book.builder()
                .id(id)
                .name("Test Book")
                .price(2000)
                .build();


        when(bookRepository.findById(id)).thenReturn(Optional.ofNullable(book));
        assert book != null;
        when(bookRepository.save(book)).thenReturn(book);
        when(modelMapper.map(book, BooksDTO.class)).thenReturn(booksDTO);

        BooksDTO result = bookService.updateBook(id,booksDTO);

        assertNotNull(result);
        assertEquals(id , result.getId());

    }
}