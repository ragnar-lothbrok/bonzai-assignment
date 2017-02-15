package com.assignment.lucene.api;

import java.util.List;

import com.assignment.lucene.dtos.Location;
import com.assignment.lucene.dtos.Vehicle;

public interface GeoLocatorService {

	List<Vehicle> getVehicleInRange(Location location, Float range);

	Boolean addLocations(List<Vehicle> vehicels);

	Boolean addLocations(Vehicle vehicel);

}
