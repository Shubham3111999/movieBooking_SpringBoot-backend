package com.movieBooking.service;

import java.util.Iterator;
import java.util.List;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieBooking.dto.TheaterDto;
import com.movieBooking.exceptions.ResourceNotFoundException;
import com.movieBooking.model.Location;
import com.movieBooking.model.Shows;
import com.movieBooking.model.Theater;
import com.movieBooking.repository.TheaterRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TheaterService {

	@Autowired
	TheaterRepository theaterRepository;

	@Autowired
	LocationService locationService;
	
	@Autowired
	ShowService showService;

	public Theater addTheater(TheaterDto theaterDto) {

		Theater theater = new Theater();

		theater.setAddress(theaterDto.getAddress());
		theater.setName(theaterDto.getName());

		// location id
		List<Location> allLocations = locationService.getAllLocations();

		for (Location location : allLocations) {
			if (location.getName().equalsIgnoreCase(theaterDto.getLocation().getName())) {
				theater.setLocation(location);
				return theaterRepository.save(theater);
			}
		}

		return theaterRepository.save(theater);

//		 theater.setLocation(theaterDto.getLocation());
//		 return theater;
	}

	public Theater getTheaterById(Long id) {
		return theaterRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Theater not found with ID: " + id));
	}

	public List<Theater> getAllTheaterByLocation(String locationName) {

		return theaterRepository.findAllByLocation_Name(locationName);
	}

	public void deleteTheaterById(int id) {
		
		Theater theater=getTheaterById((long) id);
		
		//first delete show related to theaters
		for(Shows show: theater.getShows()) {
			showService.deleteShowById(show.getId());
		}
		

		theaterRepository.deleteById((long) id);
	}

	
	public Theater updateTheaterById(TheaterDto theaterDto, int id) {
		Theater theater = getTheaterById((long)id);

		theater.setAddress(theaterDto.getAddress());
		theater.setName(theaterDto.getName());

		// location id
		List<Location> allLocations = locationService.getAllLocations();

		for (Location location : allLocations) {
			if (location.getName().equalsIgnoreCase(theaterDto.getLocation().getName())) {
				theater.setLocation(location);
				return theaterRepository.save(theater);
			}
		}

		return theaterRepository.save(theater);
	}

}
