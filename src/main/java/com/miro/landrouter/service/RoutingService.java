package com.miro.landrouter.service;

import com.miro.landrouter.model.Route;

import java.io.IOException;

public interface RoutingService {
	Route route(String origin, String destination);
}
