package com.movieBooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieBooking.dto.LocationDto;
import com.movieBooking.exceptions.ResourceAlreadyAvailable;
import com.movieBooking.exceptions.ResourceNotFoundException;
import com.movieBooking.model.Location;
import com.movieBooking.repository.LocationRpository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class LocationService {
	
	@Autowired
	LocationRpository locationRpository;

	public List<Location> getAllLocations() {
		return locationRpository.findAll();
	}

	public Location addLocation(LocationDto locationDto) {
		
		//if location already available dont add location
		if(locationRpository.findByName(locationDto.getName()).isEmpty()==false) {
			throw new ResourceAlreadyAvailable("Location already avaialble");
		}
		 
		
		
		Location location =new Location();
		location.setName(locationDto.getName());
		
		return locationRpository.save(location);
	}

	public void deleteLocation(int id) {
		locationRpository.deleteById((long) id);
	}

	public Location updateLocation(LocationDto locationDto, int id) {
		Location location =locationRpository.findById((long) id).orElseThrow(() -> new ResourceNotFoundException("Location not found with ID: " + id));
		location.setName(locationDto.getName());
		
		return locationRpository.save(location);
	}

}
