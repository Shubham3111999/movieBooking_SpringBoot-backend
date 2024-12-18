package com.movieBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieBooking.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long>{

	List<Booking> findAllByShow_Id(Long id);

}
