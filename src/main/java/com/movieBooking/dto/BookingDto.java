package com.movieBooking.dto;


import java.util.List;
import com.movieBooking.model.Seat;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingDto {
	
	@NotNull
	private int showId;
	
	@NotNull
	private String email;
	
	@NotNull
	private List<Seat> seats;

}
