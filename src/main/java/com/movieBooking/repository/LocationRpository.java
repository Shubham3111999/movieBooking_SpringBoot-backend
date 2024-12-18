package com.movieBooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieBooking.model.Location;

public interface LocationRpository extends JpaRepository<Location, Long>{
	
	public Optional<Location> findByName(String name);
}
