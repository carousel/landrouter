package com.miro.landrouter.service;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.miro.landrouter.data.Country;
import com.miro.landrouter.data.Region;
import com.miro.landrouter.exception.RouteSearchException;

@SpringBootTest
class RoutingServiceImplTest {

	@Autowired
	private RoutingService routingService;

	@MockBean
	private CountryService countryService;

	@BeforeEach
	private void beforeEach() {
		Mockito.clearInvocations(countryService);
	}

	@Test
	void testUnknownOrigin() throws IOException {
		Mockito.when(countryService.countries()).thenReturn(List.of(
			new Country("e1", Region.EUROPE, List.of("e2", "e3")),
			new Country("e2", Region.EUROPE, List.of("e1", "e3")),
			new Country("e3", Region.EUROPE, List.of("e1", "e2"))));

		var exception = Assertions.assertThrows(RouteSearchException.class,
			() -> routingService.route("BLA", "e3"));
		Assertions.assertEquals("Unknown origin country BLA", exception.getMessage());
	}

	@Test
	void testUnknownDestination() throws IOException {
		Mockito.when(countryService.countries()).thenReturn(List.of(
			new Country("e1", Region.EUROPE, List.of("e2", "e3")),
			new Country("e2", Region.EUROPE, List.of("e1", "e3")),
			new Country("e3", Region.EUROPE, List.of("e1", "e2"))));

		var exception = Assertions.assertThrows(RouteSearchException.class,
			() -> routingService.route("e1", "BLA"));
		Assertions.assertEquals("Unknown destination country BLA",
			exception.getMessage());
	}

	@Test
	void testIsolatedOrigin() throws IOException {
		Mockito.when(countryService.countries()).thenReturn(List.of(
			new Country("e1", Region.EUROPE, List.of()),
			new Country("e2", Region.EUROPE, List.of("e3")),
			new Country("e3", Region.EUROPE, List.of("e2"))));

		var exception = Assertions.assertThrows(RouteSearchException.class,
			() -> routingService.route("e1", "e3"));
		Assertions.assertEquals("Origin e1 is isolated", exception.getMessage());
	}

	@Test
	void testIsolatedDestination() throws IOException {
		Mockito.when(countryService.countries()).thenReturn(List.of(
			new Country("e1", Region.EUROPE, List.of("e2")),
			new Country("e2", Region.EUROPE, List.of("e1")),
			new Country("e3", Region.EUROPE, List.of())));

		var exception = Assertions.assertThrows(RouteSearchException.class,
			() -> routingService.route("e1", "e3"));
		Assertions.assertEquals("Destination e3 is isolated", exception.getMessage());
	}

	@Test
	void testSameOriginAndDestination() throws IOException {
		Mockito.when(countryService.countries()).thenReturn(List.of(
			new Country("e1", Region.EUROPE, List.of("e2", "e3")),
			new Country("e2", Region.EUROPE, List.of("e1", "e3")),
			new Country("e3", Region.EUROPE, List.of("e1", "e2"))));

		Assertions.assertEquals(List.of("e1"),
			routingService.route("e1", "e1").getRoutes());
	}

	@Test
	void testSameIsolatedOriginAndDestination() throws IOException {
		Mockito.when(countryService.countries()).thenReturn(List.of(
			new Country("e1", Region.EUROPE, List.of()),
			new Country("e2", Region.EUROPE, List.of()),
			new Country("e3", Region.EUROPE, List.of())));

		Assertions.assertEquals(List.of("e1"),
			routingService.route("e1", "e1").getRoutes());
	}

	@Test
	void testNonContinentalRoute() throws IOException {
		Mockito.when(countryService.countries()).thenReturn(List.of(
			new Country("e1", Region.EUROPE, List.of()),
			new Country("a1", Region.ANTARCTIC, List.of())));

		var exception = Assertions.assertThrows(RouteSearchException.class,
			() -> routingService.route("e1", "a1"));
		Assertions.assertEquals("EUROPE (e1) is not connected with ANTARCTIC (a1) by land",
			exception.getMessage());
	}

	@Test
	void testSampleRoute() throws IOException {
		Mockito.when(countryService.countries()).thenReturn(List.of(
			new Country("a1", Region.AMERICAS, List.of("a2")),
			new Country("a2", Region.AMERICAS, List.of("a1", "a3")),
			new Country("a3", Region.AMERICAS, List.of("a2", "a4")),
			new Country("a4", Region.AMERICAS, List.of("a3"))));

		Assertions.assertEquals(List.of("a1", "a2", "a3", "a4"),
			routingService.route("a1", "a4").getRoutes());
	}
}