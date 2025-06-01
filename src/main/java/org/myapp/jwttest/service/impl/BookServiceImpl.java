package org.myapp.jwttest.service.impl;

import org.modelmapper.ModelMapper;
import org.myapp.jwttest.dto.BooksDTO;
import org.myapp.jwttest.models.Book;
import org.myapp.jwttest.repository.BookRepository;
import org.myapp.jwttest.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;


    private final ModelMapper modelMapper;

    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BooksDTO> getAllBooks() {

        List<Book> bookList = bookRepository.findAll();

        if (bookList.isEmpty()) {
            return null;
        }

        return bookList.stream()
                .map(book -> modelMapper.map(book, BooksDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public BooksDTO addBook(BooksDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        Book result = bookRepository.save(book);
        return modelMapper.map(result, BooksDTO.class);
    }

    @Override
    public BooksDTO getBook(Long id) {
        Book book = bookRepository.findById(id).orElse(null);

        if (book == null) {
            return null;
        }

        return modelMapper.map(book, BooksDTO.class);
    }

    @Override
    public BooksDTO updateBook(long id, BooksDTO booksDTO) {
        Book book = bookRepository.findById(id).orElse(null);

        assert book != null;
        book.setPrice(booksDTO.getPrice());
        book.setName(booksDTO.getName());
        book.setAuthor(booksDTO.getAuthor());

        return modelMapper.map(bookRepository.save(book), BooksDTO.class);


    }
}
