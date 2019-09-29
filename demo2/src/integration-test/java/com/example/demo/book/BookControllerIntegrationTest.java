package com.example.demo.book;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerIntegrationTest {

    @Autowired
    WebTestClient testClient;

    @LocalServerPort
    int randomServerPort;

    @Test
    public void listBooks() {
        testClient.get().uri("/books").accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class).hasSize(25);
    }

    @Test
    public void addNewBook() {
        BookDto book = new BookDto(null, "a title");
        testClient.post().uri("/books").syncBody(book)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.title").isEqualTo("a title");

        testClient.get().uri("/books").accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class).hasSize(26);
    }

    @Test
    public void saveBook() {
        FluxExchangeResult<BookDto> response = testClient.get().uri("/books").accept(MediaType.TEXT_EVENT_STREAM)
                .exchange().returnResult(BookDto.class);
        BookDto firstBook = response.getResponseBody().blockFirst();
        firstBook.setTitle("new title");
        testClient.post().uri("/books").syncBody(firstBook)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.title").isEqualTo("new title");

        String url = getBaseUrl("/books/" + firstBook.getId().get());
        System.out.println(url);
        Response response2 = RestAssured.get(url);
        BookDto book = response2.then()
                .statusCode(200)
                .extract().jsonPath().getObject(".", BookDto.class);
        assertThat(book.getTitle()).isEqualTo("new title");
    }

    private String getBaseUrl(String uri) {
        return String.format("http://localhost:%s%s", randomServerPort, uri);
    }
}