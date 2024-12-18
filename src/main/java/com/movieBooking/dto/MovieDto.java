package com.movieBooking.dto;


import java.util.List;

import com.movieBooking.model.Location;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class MovieDto {
	
    private String movieName;
	
    private String posterUrl;
	
	private String genre;
	

	private List<String> cast;
	
	@Min(0)
	@Max(10)
	private Integer rating;
	

	private String duration;
	
	private String movieDetail;
	
	private List<Location> locations;

}
