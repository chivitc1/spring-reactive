package com.example.demo.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public Mono<BookDto> store(@RequestBody Mono<BookDto> book) {
        return bookService.save(book);
    }

    @GetMapping("/{id}")
    public Mono<BookDto> find(@PathVariable("id") String bookId) {
        return bookService.findById(bookId);
    }

//    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<BookDto> list() {
        return bookService.getBooks();
    }
}
