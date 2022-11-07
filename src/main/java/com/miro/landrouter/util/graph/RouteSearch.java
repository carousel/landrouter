package com.miro.landrouter.util.graph;

import java.util.*;

import com.miro.landrouter.data.Country;
import com.miro.landrouter.exception.RouteSearchException;
import com.miro.landrouter.util.stream.Collector;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class RouteSearch {

  private final Map<String, Country> countries;
  private final Country origin;
  private final Country destination;

  private final Map<Country, Boolean> visited = new HashMap<>();
  private final Map<Country, Country> previous = new HashMap<>();

  public final List<String> paths() {
    Country currentCountry = origin;

    Queue<Country> pivot = new ArrayDeque<>();
    pivot.add(currentCountry);

    visited.put(currentCountry, true);

    while (!pivot.isEmpty()) {
      currentCountry = pivot.remove();
      log.debug("Visiting country: " + currentCountry.getName());
      if (currentCountry.equals(destination)) {
        log.debug("Origin and destination are equal!!!");
        break;
      } else {
        for (var neighbour : currentCountry.getBorders()) {
          Country neighbourCountry = countries.get(neighbour);
          if(!visited.containsKey(neighbourCountry)){
            log.debug("Registering neighbour: " + neighbourCountry.getName());
            pivot.add(neighbourCountry);
            visited.put(neighbourCountry, true);
            previous.put(neighbourCountry, currentCountry);
            if (neighbourCountry.equals(destination)) {
              log.debug("Shortest path found!");
              currentCountry = neighbourCountry;
              break;
            }
          } else {
            log.debug("Skipping neighbour " + neighbourCountry.getName());
          }
        }
      }
    }

    if (!currentCountry.equals(destination)){
      throw new RouteSearchException("Cannot reach the path");
    }

    List<Country> path = new ArrayList<>();
    for (Country node = destination; node != null; node = previous.get(node)) {
      path.add(node);
    }

    return path.stream()
            .map(Country::getName)
            .collect(Collector.reversed());
  }
}