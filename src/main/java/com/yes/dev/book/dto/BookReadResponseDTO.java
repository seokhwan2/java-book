package com.yes.dev.book.dto;


import com.yes.dev.entity.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class BookReadResponseDTO {
    private Integer bookId;
    private String title;
    private Integer price;
    private LocalDateTime insertDateTime;

    public BookReadResponseDTO fromBook(Book book){
        this.bookId = book.getBookId();
        this.title = book.getTitle();
        this.price = book.getPrice();
        this.insertDateTime = book.getInsertDateTime();
        return this;
    }

    public static BookReadResponseDTO BookFactory(Book book){
        BookReadResponseDTO bookReadResponseDTO = new BookReadResponseDTO();
        bookReadResponseDTO.fromBook(book);
        return bookReadResponseDTO;
    }
}
