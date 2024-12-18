package com.movieBooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieBooking.dto.ShowDto;
import com.movieBooking.exceptions.ValidationException;
import com.movieBooking.model.Seat;
import com.movieBooking.model.Shows;
import com.movieBooking.service.ShowService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/show")
public class ShowController {
	
	@Autowired
	ShowService showService;
	
	@PreAuthorize("hasAnyRole('THOWNER', 'ADMIN')")
	@PostMapping("/addShow/{theaterId}")
	public Shows addShow(@Valid @RequestBody ShowDto showDto, @PathVariable int theaterId, BindingResult result) {
	    
	    if (result.hasErrors()) {
	        throw new ValidationException("Validation failed: " + result.getAllErrors());
	    }
	    return showService.addShow(showDto, theaterId);
	}
	
	
	@GetMapping("/getShowById/{id}")
	public Shows getShowById(@PathVariable int id) {
		return showService.getShowById(id);
	}
	
	@GetMapping("/getAllShowsByTheaterId/{theaterId}")
	public List<Shows> getAllShowsByTheaterId(@PathVariable int theaterId){
		return showService.getAllShowsByTheaterId(theaterId);
	}
	
	@PreAuthorize("hasAnyRole('THOWNER', 'ADMIN')")
	@DeleteMapping("/deleteShowById/{id}")
	public void deleteShowById(@PathVariable int id){
		showService.deleteShowById((long) id);
	}
	
	@PreAuthorize("hasAnyRole('THOWNER', 'ADMIN')")
	@PutMapping("/updateShow/{id}")
	public Shows updateShow(@RequestBody ShowDto showDto, @PathVariable int id) {
		return showService.updateShow(showDto, id);
	}
	
	@GetMapping("/getAllSeats/{showId}")
	public List<Seat> getAllSeats(@PathVariable int showId){
		return showService.getAllSeats(showId);
	}

}
