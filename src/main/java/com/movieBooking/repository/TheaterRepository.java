package com.movieBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieBooking.model.Theater;

public interface TheaterRepository extends JpaRepository<Theater, Long>{

	 List<Theater> findAllByLocation_Name(String locationName);

}
