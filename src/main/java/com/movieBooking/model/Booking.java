package com.movieBooking.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private int totalPrice;
	
	@Column
	private LocalDateTime bookingDateAndTime;
	
	@ManyToOne
	private Shows show;
	
	@OneToMany
	@JoinTable(
		name = "booking_seat", // Name of the join table
		joinColumns = @JoinColumn(name = "booking_id"), 
		inverseJoinColumns = @JoinColumn(name = "seat_id") 
	)
	private List<Seat> seats;
	
	@ManyToOne
	@JsonManagedReference
	private User user;
}
