package com.movieBooking.dto;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class MovieLikedDto {
	
	
	private Long id;
	
	private String movieName;
	
	private String posterUrl;
	
	private String genre;
	
	private List<String> cast;
	
	private Integer rating;
	
	private String duration;
	
	private String movieDetail;
	
	private boolean isLiked;
}
