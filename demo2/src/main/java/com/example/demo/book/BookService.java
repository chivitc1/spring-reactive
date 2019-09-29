package com.example.demo.book;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {
    Mono<BookDto> save(Mono<BookDto> book);
    Mono<BookDto> findById(String id);
    Flux<BookDto> getBooks();
}
