package com.movieBooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieBooking.dto.LocationDto;
import com.movieBooking.dto.MovieDto;
import com.movieBooking.model.Location;
import com.movieBooking.model.Movie;
import com.movieBooking.service.LocationService;

@RestController
@RequestMapping("/location")
public class LocationController {
	
	@Autowired
	LocationService locationService;
	

	@GetMapping("/getAllLocations")
	public List<Location> getAllLocations() {
		return locationService.getAllLocations();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addLocation")
	public Location addLocation(@RequestBody LocationDto locationDto) {
		return locationService.addLocation(locationDto);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteLocation/{id}")
	public void deleteLocation(@PathVariable int id) {
		locationService.deleteLocation(id);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateLocation/{id}")
	public Location updateLocation(@RequestBody LocationDto locationDto,@PathVariable int id) {
		return locationService.updateLocation(locationDto,id);
	}
	

}
