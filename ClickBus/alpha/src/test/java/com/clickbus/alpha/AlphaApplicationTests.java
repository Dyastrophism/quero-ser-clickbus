package com.clickbus.alpha;

import com.clickbus.alpha.api.PlaceRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AlphaApplicationTests {

	@Autowired
	WebTestClient webTestClient;

	@Test
	 void testCreatePlaceSucess() {
		var name = "Valid Name";
		var city = "Valid City";
		var state = "Valid State";

		webTestClient
				.post()
				.uri("/places")
				.bodyValue(new PlaceRequest(name, city, state))
				.exchange()
				.expectBody()
				.jsonPath("name").isEqualTo(name)
				.jsonPath("city").isEqualTo(city)
				.jsonPath("state").isEqualTo(state)
				.jsonPath("updatedAt").isNotEmpty()
				.jsonPath("createdAt").isNotEmpty();

	}

	@Test
	void testCreatePlaceFail() {
		var name = "";
		var city = "";
		var state = "";

		webTestClient
				.post()
				.uri("/places")
				.bodyValue(new PlaceRequest(name, city, state))
				.exchange()
				.expectStatus()
				.isBadRequest();
	}

}
