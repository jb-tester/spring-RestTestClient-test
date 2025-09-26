package com.mytests.spring.springresttestclienttest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BindToRouteTests {
    private RestTestClient client;
    @Autowired
    private RouterFunction<ServerResponse> route;

    @BeforeEach
    void setUp() {
        client = RestTestClient.bindToRouterFunction(route).build();
    }
    @Test
    void testGetRoute() {
        String rez = client.get().uri("/test4")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult().getResponseBody();
        assertEquals("route test4", rez);

    }
    // url is defined in the not binded controller
    @Test
    void testDifferentControllerMapping() {
        client.get().uri("/test0")
                .exchange()
                .expectStatus().isNotFound();


    }
}
