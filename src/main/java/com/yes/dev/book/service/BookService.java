package com.yes.dev.book.service;

import com.yes.dev.book.dto.BookCreateDTO;
import com.yes.dev.book.dto.BookReadResponseDTO;
import com.yes.dev.entity.Book;
import com.yes.dev.entity.BookRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BookService {
    private BookRepository bookRepository;
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public Integer insert(BookCreateDTO bookCreateDTO) {
        Book book = Book.builder()
                .title(bookCreateDTO.getTitle())
                .price(bookCreateDTO.getPrice())
                .build();

        this.bookRepository.save(book);
        return book.getBookId();
    }
    public BookReadResponseDTO read(Integer bookId) throws NoSuchElementException {
        Book book = this.bookRepository.findById(bookId).orElseThrow();
        BookReadResponseDTO bookReadResponseDTO = new BookReadResponseDTO();
        bookReadResponseDTO.fromBook(book);
        return bookReadResponseDTO;
    }
}