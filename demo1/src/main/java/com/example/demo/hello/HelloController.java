package com.example.demo.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public Mono<String> hello() {
        System.out.println("Test hello");
        return Mono.just("Hello World");
    }
}
