package com.movieBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieBooking.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
	
}
