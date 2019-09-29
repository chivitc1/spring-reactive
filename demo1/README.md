# Some concepts
- package for reactive request and response 
org.springframework.http.server.reactive package

- Return void
Mono<Void>

- ServletHttpHandlerAdapter

when running on a Servlet 3.1 container (supporting nonblocking IO) ServletHttpHandlerAdapter (or one of its subclasses) 
is used to adapt from the plain Servlet world to the Reactive world

- ReactorHttpHandlerAdapter

When running on a native Reactive engine like Netty1 the ReactorHttpHandlerAdapter is used.

- To define a controller class in Spring WebFlux, a class has to be marked with the
@Controller or @RestController annotation (just as with Spring MVC)

# Run

$ ./gradlew clean build

$ ./gradlew test

$ ./gradlew integrationTest

$ ./gradlew bootRun

$ curl -i localhost:8080/hello

