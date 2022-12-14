package com.miro.landrouter.mapper;

import java.util.List;

import com.miro.landrouter.controller.dto.CountryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.miro.landrouter.data.Country;

@SpringBootTest
class CountryMapperTest {

	@Autowired
	private CountryMapper mapper;

	@Test
	void testFromDto_oneObject() {
		var countryDto = new CountryDto("hello", "Americas", List.of("world", "three"));
		var country = mapper.fromDto(countryDto);

		assertCountry(countryDto, country);
	}

	@Test
	void testFromDto_list() {

		var countryDtoList= List.of(
			new CountryDto("one", "Asia", List.of("two", "three")),
			new CountryDto("two", "aSIa", List.of("one", "three")),
			new CountryDto("three", "ASIA", List.of("one", "two")),
			new CountryDto("four", "asia", List.of()));

		var countries = mapper.fromDto(countryDtoList);

		for (int i = 0; i < 4; i++) {
			assertCountry(countryDtoList.get(0), countries.get(0));
		}
	}

	@Test
	void testFromDtoInvalidRegion() {
		var countryDto = new CountryDto("one", "Blah", List.of());
		var country = mapper.fromDto(countryDto);

		Assertions.assertNull(country.getRegion());
	}

	static void assertCountry(CountryDto expected, Country actual) {
		Assertions.assertEquals(expected.getCca3(), actual.getName());
		Assertions.assertEquals(expected.getBorders(), actual.getBorders());
		Assertions.assertNotNull(actual.getRegion());
		Assertions.assertEquals(expected.getRegion().toUpperCase(), actual.getRegion().name());
	}
}