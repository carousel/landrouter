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

  @GetMapping("/routing/{origin}/{destination}")
  public ResponseEntity<Route> getRoute(@PathVariable String origin, @PathVariable String destination) {
    Route route = routingService.route(origin, destination);
    return ResponseEntity.ok(route);
  }
}