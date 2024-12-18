package com.movieBooking.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.movieBooking.model.Movie;
import com.movieBooking.model.Theater;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
public class LocationDto {
	
	private String name;
	
}
