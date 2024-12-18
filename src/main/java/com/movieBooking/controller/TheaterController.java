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

import com.movieBooking.dto.TheaterDto;
import com.movieBooking.model.Theater;
import com.movieBooking.service.TheaterService;

@RestController
@RequestMapping("/theater")
public class TheaterController {
	
	@Autowired
	TheaterService theaterService;
	
	@PreAuthorize("hasAnyRole('THOWNER', 'ADMIN')")
	@PostMapping("/addTheater")
	public Theater addTheater(@RequestBody TheaterDto theaterDto) {
		return theaterService.addTheater(theaterDto);
	}
	
	
	@GetMapping("/getTheaterById/{id}")
	public Theater getTheaterById(@PathVariable int id) {
		return theaterService.getTheaterById((long) id);
	}
	
	@GetMapping("/getAllTheaterByLocation/{locationName}")
	public List<Theater> getAllTheaterByLocation(@PathVariable String locationName){
		return theaterService.getAllTheaterByLocation(locationName);
	}
	
	@PreAuthorize("hasAnyRole('THOWNER', 'ADMIN')")
	@DeleteMapping("/deleteTheaterById/{id}")
	public void deleteTheaterById(@PathVariable int id) {
		theaterService.deleteTheaterById(id);
	}
	
	@PreAuthorize("hasAnyRole('THOWNER', 'ADMIN')")
	@PutMapping("/updateTheaterById/{id}")
	public Theater updateTheaterById(@RequestBody TheaterDto theaterDto,@PathVariable int id) {
		return theaterService.updateTheaterById(theaterDto,id);
	}

}
