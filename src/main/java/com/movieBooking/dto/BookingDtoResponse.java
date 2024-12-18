package com.movieBooking.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.movieBooking.model.Seat;
import com.movieBooking.model.Shows;
import com.movieBooking.model.User;


import lombok.Data;

@Data
public class BookingDtoResponse {
	
	private Long id;
	
	private int totalPrice;
	
	private LocalDateTime bookingDateAndTime;
	
	private Shows show;
	
	private List<Seat> seats;
	
	private User user;
	
	private String theater;
	
	private String movie;


}
