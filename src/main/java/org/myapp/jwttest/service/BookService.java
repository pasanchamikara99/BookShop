package org.myapp.jwttest.service;

import org.myapp.jwttest.dto.BooksDTO;

import java.util.List;


public interface BookService {

    List<BooksDTO> getAllBooks();

    BooksDTO addBook(BooksDTO bookDTO);

    BooksDTO getBook(Long id);

    BooksDTO updateBook(long id, BooksDTO booksDTO);
}
