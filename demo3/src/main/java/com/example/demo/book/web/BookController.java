package com.example.demo.book.web;

import com.example.demo.book.BookDto;
import com.example.demo.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public Mono<String> list(Model model) {
        Flux<BookDto> books = bookService.getBooks();
//        model.addAttribute("books", books);
        final int numOfLoadElements = 5;
        model.addAttribute("books", new ReactiveDataDriverContextVariable(books, numOfLoadElements));
        return Mono.just("books/list");
    }

}
