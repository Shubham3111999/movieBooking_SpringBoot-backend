package com.movieBooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieBooking.exceptions.ResourceNotFoundException;
import com.movieBooking.model.Seat;
import com.movieBooking.repository.SeatRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SeatService {
	
	@Autowired
	SeatRepository seatRepository;

	public Seat getSeatById(long id) {
		return seatRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Seat not found with ID: " + id));
	}

}
