package com.assignment;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.assignment.CabSearchApplication;
import com.assignment.lucene.api.GeoLocatorService;
import com.assignment.lucene.dtos.Location;
import com.assignment.lucene.dtos.SearchDto;
import com.assignment.lucene.dtos.Vehicle;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
	classes = CabSearchApplication.class,
	loader = SpringApplicationContextLoader.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GeoServiceTest {

	@Autowired
	private GeoLocatorService geoLocatorService;

	@Test
	public void searchInRange() {
		Location location = new Location();
		location.setLatitude(40.922326f);
		location.setLongitude(-72.637078f);
		SearchDto searchDto = new SearchDto();
		searchDto.setRange(10.33434f);
		searchDto.setLocation(location);
		List<Vehicle> vehicleList = geoLocatorService.getVehicleInRange(searchDto.getLocation(), searchDto.getRange());
		assertNotNull(vehicleList);
	}
	
	@Test
	public void test() {
		Location location = new Location();
		location.setLatitude(40.922326f);
		location.setLongitude(-72.637078f);
		Vehicle vehicle = new Vehicle();
		vehicle.setLocation(location);
		vehicle.setVehicleNumber("Holtsville-00501");
		List<Vehicle> vehicleList = new ArrayList<Vehicle>();
		vehicleList.add(vehicle);
		System.out.println(new Gson().toJson(vehicleList));
		Boolean result = geoLocatorService.addLocations(vehicle);
		assertNotNull(result);
	}

}
