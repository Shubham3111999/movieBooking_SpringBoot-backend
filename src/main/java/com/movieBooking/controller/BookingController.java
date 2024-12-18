package com.movieBooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieBooking.dto.BookingDto;
import com.movieBooking.exceptions.ValidationException;
import com.movieBooking.model.Booking;
import com.movieBooking.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/booking")
public class BookingController {
	
	@Autowired
	BookingService bookingService;
	
	
	@PostMapping("/addBooking")
	public Booking addBooking(@Valid @RequestBody BookingDto bookingDto, BindingResult result) {
		
		 if (result.hasErrors()) {
		        throw new ValidationException("Validation failed: " + result.getAllErrors());
		   }
		
		return bookingService.addBooking(bookingDto);
	}
	
	
	@DeleteMapping("/cancelBooking/{bookingId}")
	public String cancelBooking(@PathVariable int bookingId) {
		return bookingService.cancelBooking(bookingId);
	}
}
