package com.yes.dev.book.controller;

import com.yes.dev.book.dto.BookLogCreateDTO;
import com.yes.dev.book.dto.BookLogCreateResponseDTO;
import com.yes.dev.book.service.BookLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book-log")
public class BookLogController {
    private BookLogService bookLogService;

    @Autowired
    public void setBookLogService(BookLogService bookLogService) {
        this.bookLogService = bookLogService;

    }

    @PostMapping("/create")
    public ResponseEntity<BookLogCreateResponseDTO> insert(@RequestBody BookLogCreateDTO bookLogCreateDTO) {
        BookLogCreateResponseDTO bookLogCreateResponseDTO = this.bookLogService.insert(bookLogCreateDTO);
        return ResponseEntity.ok(bookLogCreateResponseDTO);
    }
}