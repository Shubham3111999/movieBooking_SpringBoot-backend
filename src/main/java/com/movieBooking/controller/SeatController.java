package com.movieBooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieBooking.model.Seat;
import com.movieBooking.model.Theater;
import com.movieBooking.service.SeatService;

@RestController
@RequestMapping("/seat")
public class SeatController {
	
	@Autowired
	SeatService seatService;
	
	@GetMapping("/getSeatById/{id}")
	public Seat getSeatById(@PathVariable int id) {
		return seatService.getSeatById((long) id);
	}
}
