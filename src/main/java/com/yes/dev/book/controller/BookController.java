package com.yes.dev.book.controller;

import com.yes.dev.book.dto.BookCreateDTO;
import com.yes.dev.book.dto.BookReadResponseDTO;
import com.yes.dev.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.NoSuchElementException;


@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/book/create")
    public String create(){
        //create() 메소드는 브라우저에서 /book/create 주소가 HTTP GET방식으로 입력되었을 때 book/create
        // 경로의 뷰를 보여주는 컨트롤러 메소드입니다.
        return "book/create";
    }

    //@PostMapping 어노테이션은 http 요청 메소드 중 POST 메소드로 요청될 때만 아래의 메소드가 실행된다는 의미입니다.
    //POST 메소드는 데이터를 생성할 때 사용하는 메소드입니다. 다만 웹 브라우저에서는 GET과 POST밖에 사용하지 않기 때문에
    // POST로 데이터 변경에 필요한 액션들(생성, 수정, 삭제)을 할 때 사용됩니다.
    @PostMapping("/book/create")
    public String insert(BookCreateDTO bookCreateDTO){
        Integer bookId = this.bookService.insert(bookCreateDTO);
        return String.format("redirect:/book/read/%s", bookId);
    }

    @GetMapping("/book/read/{bookId}")
    public ModelAndView read(@PathVariable Integer bookId) {
        ModelAndView mav = new ModelAndView();

        try {
            BookReadResponseDTO bookReadResponseDTO = this.bookService.read(bookId);
            mav.addObject("bookReadResponseDTO", bookReadResponseDTO);
            mav.setViewName("book/read");

        }catch(NoSuchElementException ex) {
            mav.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
            mav.addObject("message", "책 정보가 없습니다.");
            mav.addObject("location", "/book");
            mav.setViewName("common/error/422");
        }

        return mav;
    }
}
