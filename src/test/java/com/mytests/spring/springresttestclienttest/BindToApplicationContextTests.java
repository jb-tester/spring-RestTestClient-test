package com.mytests.spring.springresttestclienttest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class BindToApplicationContextTests {

	private RestTestClient client;

	private final WebApplicationContext context;


	public BindToApplicationContextTests(WebApplicationContext context) {
		this.context = context;
	}


	@BeforeEach
	void setUp() {
		this.client = RestTestClient.bindToApplicationContext(context).build();
	}


	@Test
	void testFromOtherController() {
		this.client.get().uri("/test0")
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class).isEqualTo("test0");
	}

	@Test
	void testFromFooController() {
		this.client.get().uri("/test1?reqparam=test1")
				.exchange()
				.expectStatus().isOk()
				.expectBody().jsonPath("$.reqparam").isEqualTo("test1");
	}

	@Test
	void testFromRouter() {
		this.client.get().uri("/test5")
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class).isEqualTo("route test5");
	}

}