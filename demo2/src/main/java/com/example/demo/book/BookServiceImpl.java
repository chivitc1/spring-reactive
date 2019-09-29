package com.example.demo.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final Map<String, Book> books = new ConcurrentHashMap<>(10);

    private final BookGenerator bookGenerator;

    @Autowired
    public BookServiceImpl(BookGenerator bookGenerator) {
        this.bookGenerator = bookGenerator;
    }

    @PostConstruct
    public void init() {
        for (int i=0; i<25; i++) {
            Book book = bookGenerator.generate();
            books.put(book.getId(), book);
        }
    }

    @Override
    public Mono<BookDto> save(Mono<BookDto> book) {
        Mono<Book> result = book.map(this::save);
        return result.map(it -> new BookDto(Optional.of(it.getId()), it.getTitle()));
    }

    private Book save(BookDto book) {
        if (book.getId().isPresent() && !books.containsKey(book.getId().get())) {
            throw new RuntimeException("Not Found");
        }
        if (book.getId().isPresent()) {
            Book updatingBook = new Book(book.getId().get(), book.getTitle());
            books.put(book.getId().get(), updatingBook);
            return updatingBook;
        }
        Book newBook = new Book(bookGenerator.generateId(), book.getTitle());
        books.put(newBook.getId(), newBook);
        return newBook;
    }

    @Override
    public Mono<BookDto> findById(String id) {
        return Mono.justOrEmpty(books.get(id)).map(it -> new BookDto(Optional.of(it.getId()), it.getTitle()));
    }

    @Override
    public Flux<BookDto> getBooks() {
        Flux<BookDto> result = Flux.fromIterable(books.values().stream()
                .map(it -> new BookDto(Optional.of(it.getId()), it.getTitle()))
                .collect(Collectors.toList()))
                .delayElements(Duration.ofMillis(128));
        return result;
    }
}
