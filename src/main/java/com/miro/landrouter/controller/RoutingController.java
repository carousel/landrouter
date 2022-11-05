package com.miro.landrouter.controller;

import com.miro.landrouter.data.Country;
import com.miro.landrouter.model.Route;
import com.miro.landrouter.service.CountryService;
import com.miro.landrouter.service.RoutingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor
public class RoutingController {

  private final RoutingService routingService;

  private final CountryService countryService;

  @GetMapping("/routing/{origin}/{destination}")
  public ResponseEntity<Route> getRoute(@PathVariable String origin, @PathVariable String destination) throws IOException {
    var route = routingService.route(origin, destination);
    return ResponseEntity.ok(route);
  }
  @GetMapping("/countries")
  public ResponseEntity<List<Country>> getCountries() throws IOException {
    var countries = countryService.countries();
    return ResponseEntity.ok(countries);
  }
}