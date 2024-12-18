package com.movieBooking.dto;

import com.movieBooking.model.Location;

import lombok.Data;


@Data
public class TheaterDto {
	
	
	private String name;
	
	private String address;
	
	private Location location;

}
