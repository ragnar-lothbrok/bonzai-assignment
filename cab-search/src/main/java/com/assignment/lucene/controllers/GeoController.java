package com.assignment.lucene.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.assignment.lucene.api.GeoLocatorService;
import com.assignment.lucene.dtos.SearchDto;
import com.assignment.lucene.dtos.Vehicle;

@Controller
@RequestMapping("/geo")
public class GeoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeoController.class);

	@Autowired
	private GeoLocatorService geoLocatorService;

	@RequestMapping(
		value = "/index",
		method = RequestMethod.POST,
		produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> startIndexing(@RequestBody List<Vehicle> vehicleList, HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("Received request in add locations method.");
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean result = geoLocatorService.addLocations(vehicleList);
		map.put("status", result);
		return map;
	}

	@RequestMapping(
		value = "/search",
		method = RequestMethod.POST,
		produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<Vehicle> search(@RequestBody SearchDto searchDto, HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("Received request in add locations method.");
		List<Vehicle> result = geoLocatorService.getVehicleInRange(searchDto.getLocation(), searchDto.getRange());
		return result;
	}
}
