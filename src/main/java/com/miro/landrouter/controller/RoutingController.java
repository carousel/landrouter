package com.miro.landrouter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miro.landrouter.controller.dto.CountryDto;
import com.miro.landrouter.data.Country;
import com.miro.landrouter.mapper.CountryMapper;
import com.miro.landrouter.model.Route;
import com.miro.landrouter.service.CountryService;
import com.miro.landrouter.service.RoutingService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@AllArgsConstructor
public class RoutingController {

  private final RoutingService routingService;

  private final CountryService countryService;

  private final CountryMapper countryMapper;

  @GetMapping("/routing/{origin}/{destination}")
  public ResponseEntity<Route> getRoute(@PathVariable String origin, @PathVariable String destination) throws IOException {
    var route = routingService.route(origin, destination);
    return ResponseEntity.ok(route);
  }
  @GetMapping("/countries")
  public ResponseEntity<List<Country>> getCountries() throws IOException {

    ObjectMapper mapper = new ObjectMapper();
		String filePath = new ClassPathResource("classpath:countries.json").getPath();
		Path path = ResourceUtils.getFile("classpath:countries.json").toPath();
		Object[] objects = mapper.readValue(path.toFile(), Object[].class);
//    System.out.println("size: " + objects.length);
    for (Object object : objects) {
      System.out.println(object);
    }
//    countryMapper.fromDto(List.of(objects));

//    for (CountryDto object : objects) {
//      System.out.println(object);
//    }

//    var countries = countryService.countries();
//    return ResponseEntity.ok(countries);
    return null;
  }
}