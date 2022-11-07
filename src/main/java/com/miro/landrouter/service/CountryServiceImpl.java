package com.miro.landrouter.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miro.landrouter.controller.dto.CountryDto;

import com.miro.landrouter.mapper.CountryMapper;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.io.ClassPathResource;
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
@NoArgsConstructor
public class CountryServiceImpl implements CountryService {

	@Value("${data.apipath}")
	private String apipath;
	@Autowired
	private CountryMapper countryMapper;

	@Override
	public List<Country> countries() {

		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);
		ResponseEntity<CountryDto[]> countries = restTemplate.getForEntity(apipath, CountryDto[].class);

		return countryMapper.fromDto(List.of(Objects.requireNonNull(countries.getBody())));
	}
}
