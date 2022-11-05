package com.miro.landrouter.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miro.landrouter.data.Country;

public interface CountryService {

	List<Country> countries() throws IOException;
}
