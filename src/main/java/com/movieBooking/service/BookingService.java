package com.movieBooking.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movieBooking.dto.BookingDto;
import com.movieBooking.exceptions.ResourceNotFoundException;
import com.movieBooking.model.Booking;
import com.movieBooking.model.Seat;
import com.movieBooking.repository.BookingRepository;
import com.movieBooking.repository.SeatRepository;


@Service
@Transactional
public class BookingService {

	@Autowired
	ShowService showService;

	@Autowired
	UserService userService;

	@Autowired
	SeatService seatService;
	
	@Autowired
	SeatRepository seatRepository;

	@Autowired
	BookingRepository bookingRepository;
	
	

//	@Autowired
//	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private  RabbitTemplate rabbitTemplate;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
//	@Autowired
//	UserDetails userDetails;
	
	
	
	@Transactional(timeout = 10)
	public Booking addBooking(BookingDto bookingDto) {

		Booking booking = new Booking();

		booking.setShow(showService.getShowById(bookingDto.getShowId()));
		//booking.setUser(userService.getUserById(bookingDto.getUserId()));///need to get from userDetailservice
		booking.setUser(userService.getUserByEmail(bookingDto.getEmail()));
		
		int totalPrice = 0;
		List<Seat> seats = new ArrayList<Seat>(); // set list

		String seatsString = "";

		
		for (Seat seat : bookingDto.getSeats()) {
			
			 Seat lockedSeat = seatRepository.findByIdWithLock(seat.getId())
		                .orElseThrow(() -> new ResourceNotFoundException("Seat not found with ID: " + seat.getId()));
			
			 if (lockedSeat.getIsBooked()) {
	                throw new IllegalStateException("Seat " + lockedSeat.getSeatNumber() + " is already booked!");
	            }
			 
			 lockedSeat.setIsBooked(true);

			seats.add(lockedSeat); // add seat to created seat

			totalPrice += lockedSeat.getPrice();

			seatsString += lockedSeat.getSeatNumber() + "";
		}

		booking.setSeats(seats);
		booking.setTotalPrice(totalPrice);
		booking.setBookingDateAndTime(LocalDateTime.now());

		rabbitTemplate.convertAndSend("booking-exchange", "booking.key",
				booking.getUser().getEmail() + ":" + booking.getShow().getMovie().getMovieName() + ":"
						+ booking.getShow().getTheater().getName() + ":" + booking.getShow().getTheater().getAddress()
						+ ":" + seatsString + ":" + booking.getTotalPrice());

		messagingTemplate.convertAndSend("/topic/seats/"+bookingDto.getShowId(), seats); // websocket

		return bookingRepository.save(booking);
	}

	public String cancelBooking(int bookingId) {
		Booking booking = bookingRepository.findById((long) bookingId)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));

		String seatsString = "";

		Seat bookedSeat = null;
		for (Seat seat : booking.getSeats()) {

			bookedSeat = seatService.getSeatById(seat.getId());

			bookedSeat.setIsBooked(false); // make booked seat false

			seatsString += bookedSeat.getSeatNumber() + "";

		}

		messagingTemplate.convertAndSend("/topic/seats/"+booking.getShow().getId(), booking.getSeats()); // websocket

		rabbitTemplate.convertAndSend("booking-exchange", "cancelbooking.key",
				booking.getUser().getEmail() + ":" + booking.getShow().getMovie().getMovieName() + ":"
						+ booking.getShow().getTheater().getName() + ":" + booking.getShow().getTheater().getAddress()
						+ ":" + seatsString + ":" + booking.getTotalPrice());

		bookingRepository.deleteById((long) bookingId);

		return "Booking cancelled !";
	}

}
