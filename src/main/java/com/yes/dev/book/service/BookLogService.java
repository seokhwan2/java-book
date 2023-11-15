package com.yes.dev.book.service;

import com.yes.dev.book.dto.BookLogCreateDTO;
import com.yes.dev.book.dto.BookLogCreateResponseDTO;
import com.yes.dev.entity.Book;
import com.yes.dev.entity.BookLog;
import com.yes.dev.entity.BookLogRepository;
import com.yes.dev.entity.BookRepository;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;

@Service
public class BookLogService {
    private BookRepository bookRepository;
    private BookLogRepository bookLogRepository;

    public BookLogService(BookRepository bookRepository, BookLogRepository bookLogRepository) {
        this.bookRepository = bookRepository;
        this.bookLogRepository = bookLogRepository;
    }

    public BookLogCreateResponseDTO insert(BookLogCreateDTO bookLogCreateDTO) {
        Book book = this.bookRepository.findById(bookLogCreateDTO.getBookId()).orElseThrow();

        BookLog bookLog = BookLog.builder()
                .book(book)
                .comment(bookLogCreateDTO.getComment())
                .page(bookLogCreateDTO.getPage())
                .build();

        bookLog = this.bookLogRepository.save(bookLog);

        return BookLogCreateResponseDTO.BookLogFactory(bookLog);
    }
}
