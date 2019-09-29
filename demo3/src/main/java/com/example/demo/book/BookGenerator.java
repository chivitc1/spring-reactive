package com.example.demo.book;

import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class BookGenerator {
    private static AtomicInteger currentMaxIdValue = new AtomicInteger(0);
    public Book generate() {
        String title = "Book title " + UUID.randomUUID().toString();
        Integer id = currentMaxIdValue.addAndGet(1);
        return new Book(id.toString(), title);
    }

    public String generateId() {
        Integer result = currentMaxIdValue.addAndGet(1);
        return result.toString();
    }
}
