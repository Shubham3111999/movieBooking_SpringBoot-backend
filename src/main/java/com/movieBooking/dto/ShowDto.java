package com.movieBooking.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.movieBooking.model.Movie;
import com.movieBooking.model.Seat;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShowDto {
	
    @NotNull(message = "Movie ID cannot be null")
    private int movieId;
    
    @NotNull(message = "Seats can be null")
    private List<Seat> seats;

    @NotEmpty(message = "Date cannot be empty")
    private String date;

    @NotEmpty(message = "Time cannot be empty")
    private String time;  // Ensures the time is not empty


}
