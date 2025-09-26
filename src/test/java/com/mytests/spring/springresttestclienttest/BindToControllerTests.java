package com.mytests.spring.springresttestclienttest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BindToControllerTests {

    private RestTestClient client;


    @BeforeEach
    void setUp() {
        this.client = RestTestClient.bindToController(new FooController()).build();
    }

    @Test
    void testValidPathMethod() {
        client.get().uri("/test1?reqparam=some param")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.method").isEqualTo("GET")
        ;
    }

    @Test
    void testValidPathReqParameter() {
        client.get().uri("/test1?reqparam=some param")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.reqparam").isEqualTo("some param")
        ;
    }

    @Test
    void testValidPathUri() {
        client.method(HttpMethod.GET).uri("/test1?reqparam=some param")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.uri").isEqualTo("/test1")
        ;
    }

    // defined in not-binded controller
    @Test
    void testInvalidPath() {
        client.get().uri("/test0")
                .exchange()
                .expectStatus().isNotFound()
        ;
    }

    @Test
    void testWithPathVariables() {
        client.get().uri("/test2/{id}-{name}", 1, "test")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.name").isEqualTo("test");
    }
    @Test
    void testWithPathVariables2() {
        client.get().uri("/test2/{id}-{name}", Map.of("id", 1, "name", "test"))
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.id").isEqualTo("1");
    }
    @Test
    void testResultContent() {
        String body = "some body";
        EntityExchangeResult<String> result = client.post().uri("/test3")
                .body(body)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult();
        assertThat(result.getResponseBody()).isEqualTo("some body was passed");
    }

    @Test
    void testMutateWithBaseUrl() {
        client.mutate()
                .baseUrl("/test1?reqparam=some param")
                .build().get()
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.uri").isEqualTo("/test1");
    }

    @Test
    void testMutateWithBuilder() {
        client.mutate()
                .uriBuilderFactory(new DefaultUriBuilderFactory("/test1?reqparam=some param"))
                .build().get()
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.uri").isEqualTo("/test1");
    }
}
