package com.example.demo.book;

import lombok.*;

import java.util.Optional;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode
public class BookDto {
    private Optional<String> id = Optional.empty();
    private String title;
}
