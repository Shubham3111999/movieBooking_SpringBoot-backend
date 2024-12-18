package com.movieBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieBooking.model.Shows;

public interface ShowRepository extends JpaRepository<Shows, Long> {

}
