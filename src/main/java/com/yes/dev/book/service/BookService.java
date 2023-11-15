package com.yes.dev.book.service;

import com.yes.dev.book.dto.*;
import com.yes.dev.entity.Book;
import com.yes.dev.entity.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

    /*
    * 책서비스 조회
    * */
    public BookReadResponseDTO read(Integer bookId) throws NoSuchElementException {
        Book book = this.bookRepository.findById(bookId).orElseThrow();
        BookReadResponseDTO bookReadResponseDTO = new BookReadResponseDTO();
        bookReadResponseDTO.fromBook(book);
        return bookReadResponseDTO;
    }

    /*
    * 책 수정
    * */
    public BookEditResponseDTO edit(Integer bookId) throws NoSuchElementException {
        Book book = this.bookRepository.findById(bookId).orElseThrow();
        return BookEditResponseDTO.BookFactory(book);
    }

    /*
     * 책 수정
     * */
    public void update(BookEditDTO bookEditDTO) throws NoSuchElementException {
        Book book = this.bookRepository.findById(bookEditDTO.getBookId()).orElseThrow();
        book = bookEditDTO.fill(book);
        this.bookRepository.save(book);
    }

    /*
    * 삭제기능
    * */
    public void delete(Integer bookId) throws NoSuchElementException {
        Book book = this.bookRepository.findById(bookId).orElseThrow();
        this.bookRepository.delete(book);
    }

    public List<BookListResponseDTO> bookList(String title, Integer page){
        final int pageSize = 10;

        List<Book> books;

        if (page == null) {
            page = 0;
        }else {
            page -= 1;
        }

        if (title == null) {
            Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "insertDateTime");
            books = this.bookRepository.findAll(pageable).toList();
        }
        else {
            Pageable pageable = PageRequest.of(page, pageSize);
            Sort sort = Sort.by(Order.desc("insertDateTime"));
            pageable.getSort().and(sort);
            books = this.bookRepository.findByTitleContains(title, pageable);
        }

        return books.stream().map(book ->
                new BookListResponseDTO(book.getBookId(), book.getTitle())
        ).collect(Collectors.toList());
    }

}