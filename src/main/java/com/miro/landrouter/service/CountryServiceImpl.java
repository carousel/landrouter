package com.miro.landrouter.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.miro.landrouter.controller.dto.CountryDto;

import com.miro.landrouter.mapper.CountryMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;

import com.miro.landrouter.data.Country;

import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;


@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {

	private final CountryMapper countryMapper;

	@Override
	public List<Country> countries() throws IOException {

		RestTemplate restTemplate = new RestTemplate();

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);
		ResponseEntity<CountryDto[]> countries = restTemplate.getForEntity("https://raw.githubusercontent.com/mledoze/countries/master/countries.json", CountryDto[].class);
		return countryMapper.fromDto(List.of(countries.getBody()));
	}
}
