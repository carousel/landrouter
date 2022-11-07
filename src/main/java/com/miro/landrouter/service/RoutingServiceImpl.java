package com.miro.landrouter.service;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.miro.landrouter.model.Route;
import com.miro.landrouter.util.graph.RouteSearch;
import org.springframework.stereotype.Service;

import com.miro.landrouter.data.Country;
import com.miro.landrouter.exception.RouteSearchException;

import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class RoutingServiceImpl implements RoutingService {

	private final CountryService countryService;

	@Override
	public Route route(String origin, String destination) {

		var countries = countryService.countries().stream()
			.collect(Collectors.toMap(Country::getName, Function.identity()));

		var originCountry = Optional.ofNullable(countries.get(origin))
			.orElseThrow(() -> new RouteSearchException(String.format("Unknown origin country %s", origin)));

		var destinationCountry = Optional.ofNullable(countries.get(destination))
			.orElseThrow(() -> new RouteSearchException(String.format("Unknown destination country %s", destination)));

		if (!originCountry.getRegion().connectedWith(destinationCountry.getRegion())) {
			throw new RouteSearchException(String.format(
				"%s (%s) is not connected with %s (%s) by land",
				originCountry.getRegion(), origin,
				destinationCountry.getRegion(), destination));
		}

		if (!origin.equals(destination)) {
			if (originCountry.getBorders().isEmpty()) {
				throw new RouteSearchException(String.format("Origin %s is isolated", origin));
			}

			if (destinationCountry.getBorders().isEmpty()) {
				throw new RouteSearchException(String.format("Destination %s is isolated", destination));
			}
		}

		var routes = new RouteSearch(countries, originCountry, destinationCountry).paths();

		var route = new Route();
		route.setRoutes(routes);
		return route;
	}
}
