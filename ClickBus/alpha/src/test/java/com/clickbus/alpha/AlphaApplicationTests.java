package com.clickbus.alpha;

import com.clickbus.alpha.api.PlaceRequest;
import com.clickbus.alpha.domain.Place;
import com.clickbus.alpha.domain.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
class AlphaApplicationTests {

	public static final Place CENTRAL_PARK = new Place(
			1L, "Central Park", "central-park", "New York", "New York", null, null);

	@Autowired
	WebTestClient webTestClient;

	@Autowired
	PlaceRepository placeRepository;

	@Test
	void testCreatePlaceSucess() {
		final String name = "Valid Name";
		final String city = "Valid City";
		final String state = "Valid State";
		final String slug = "valid-name";

		webTestClient
				.post()
				.uri("/places")
				.bodyValue(new PlaceRequest(name, city, state))
				.exchange()
				.expectBody()
				.jsonPath("name").isEqualTo(name)
				.jsonPath("city").isEqualTo(city)
				.jsonPath("state").isEqualTo(state)
				.jsonPath("slug").isEqualTo(slug)
				.jsonPath("updatedAt").isNotEmpty()
				.jsonPath("createdAt").isNotEmpty();

	}

	@Test
	void testCreatePlaceFail() {
		final String name = "";
		final String city = "";
		final String state = "";

		webTestClient
				.post()
				.uri("/places")
				.bodyValue(new PlaceRequest(name, city, state))
				.exchange()
				.expectStatus()
				.isBadRequest();
	}

	@Test
	void testEditPlaceSuccess() {
		final String newName = "New Name";
		final String newCity = "New City";
		final String newState = "New State";
		final String newSlug = "new-name";

		webTestClient
				.put()
				.uri("/places/1")
				.bodyValue(new PlaceRequest(newName, newCity, newState))
				.exchange()
				.expectBody()
				.jsonPath("name").isEqualTo(newName)
				.jsonPath("city").isEqualTo(newCity)
				.jsonPath("state").isEqualTo(newState)
				.jsonPath("slug").isEqualTo(newSlug)
				.jsonPath("updatedAt").isNotEmpty();

		webTestClient
				.patch()
				.uri("/places/1")
				.bodyValue(
						new PlaceRequest(CENTRAL_PARK.name(), null, null))
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("name").isEqualTo(CENTRAL_PARK.name())
				.jsonPath("city").isEqualTo(newCity)
				.jsonPath("state").isEqualTo(newState)
				.jsonPath("slug").isEqualTo(CENTRAL_PARK.slug())
				.jsonPath("createdAt").isNotEmpty()
				.jsonPath("updatedAt").isNotEmpty();


		webTestClient
				.patch()
				.uri("/places/1")
				.bodyValue(
						new PlaceRequest(null, CENTRAL_PARK.city(), null))
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("name").isEqualTo(CENTRAL_PARK.name())
				.jsonPath("city").isEqualTo(CENTRAL_PARK.city())
				.jsonPath("state").isEqualTo(newState)
				.jsonPath("slug").isEqualTo(CENTRAL_PARK.slug())
				.jsonPath("createdAt").isNotEmpty()
				.jsonPath("updatedAt").isNotEmpty();

		webTestClient
				.patch()
				.uri("/places/1")
				.bodyValue(
						new PlaceRequest(null, null, CENTRAL_PARK.state()))
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("name").isEqualTo(CENTRAL_PARK.name())
				.jsonPath("city").isEqualTo(CENTRAL_PARK.city())
				.jsonPath("state").isEqualTo(CENTRAL_PARK.state())
				.jsonPath("slug").isEqualTo(CENTRAL_PARK.slug())
				.jsonPath("createdAt").isNotEmpty()
				.jsonPath("updatedAt").isNotEmpty();
	}

	@Test
	void testGetSuccess() {
		webTestClient
				.get()
				.uri("/places/1")
				.exchange()
				.expectBody()
				.jsonPath("name").isEqualTo(CENTRAL_PARK.name())
				.jsonPath("city").isEqualTo(CENTRAL_PARK.city())
				.jsonPath("state").isEqualTo(CENTRAL_PARK.state())
				.jsonPath("slug").isEqualTo(CENTRAL_PARK.slug())
				.jsonPath("createdAt").isNotEmpty()
				.jsonPath("updatedAt").isNotEmpty();
	}

	@Test
	void testListAllSuccess() {
		webTestClient
				.get()
				.uri("/places")
				.exchange()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$[0].name").isEqualTo(CENTRAL_PARK.name())
				.jsonPath("$[0].city").isEqualTo(CENTRAL_PARK.city())
				.jsonPath("$[0].state").isEqualTo(CENTRAL_PARK.state())
				.jsonPath("$[0].slug").isEqualTo(CENTRAL_PARK.slug())
				.jsonPath("$[0].createdAt").isNotEmpty()
				.jsonPath("$[0].updatedAt").isNotEmpty();
	}

	@Test
	void testListByNameSuccess() {
		webTestClient
				.get()
				.uri("/places?name=Central Park")
				.exchange()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$[0].name").isEqualTo(CENTRAL_PARK.name())
				.jsonPath("$[0].city").isEqualTo(CENTRAL_PARK.city())
				.jsonPath("$[0].state").isEqualTo(CENTRAL_PARK.state())
				.jsonPath("$[0].slug").isEqualTo(CENTRAL_PARK.slug())
				.jsonPath("$[0].createdAt").isNotEmpty()
				.jsonPath("$[0].updatedAt").isNotEmpty();
	}

	@Test
	void testListByNameNotFound() {
		webTestClient
				.get()
				.uri("/places?name=name")
				.exchange()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$.length()").isEqualTo(0);
	}
}
