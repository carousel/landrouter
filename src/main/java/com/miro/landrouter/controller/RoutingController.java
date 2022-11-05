package com.miro.landrouter.controller;

import com.miro.landrouter.model.Route;
import com.miro.landrouter.service.RoutingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Slf4j
@Controller
@AllArgsConstructor
public class RoutingController {

  private final RoutingService routingService;
  @GetMapping("/routing/{origin}/{destination}")
  public ResponseEntity<Route> originDestination(@PathVariable String origin, @PathVariable String destination) throws IOException {
    var route = routingService.route(origin, destination);
    return ResponseEntity.ok(route);
  }
}