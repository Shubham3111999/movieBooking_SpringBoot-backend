package com.movieBooking.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieBooking.dto.ShowDto;
import com.movieBooking.exceptions.ResourceNotFoundException;
import com.movieBooking.exceptions.ValidationException;
import com.movieBooking.model.Booking;
import com.movieBooking.model.Movie;
import com.movieBooking.model.Seat;
import com.movieBooking.model.Shows;
import com.movieBooking.model.Theater;
import com.movieBooking.repository.BookingRepository;
import com.movieBooking.repository.ShowRepository;
import com.movieBooking.repository.TheaterRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ShowService {

	@Autowired
	ShowRepository showRepository;
	
	@Autowired
	TheaterService theaterService;
	
	@Autowired
	MovieService movieService;
	
	@Autowired
	TheaterRepository theaterRepository;
	
	@Autowired
	BookingRepository bookingRepository;

	public Shows addShow(ShowDto showDto, int theaterId) {
		
		 // Get theater
	    Theater theater = theaterService.getTheaterById((long) theaterId);

	    // Parse and validate date and time
	    LocalDate parsedDate = LocalDate.parse(showDto.getDate());
	    LocalTime parsedTime = LocalTime.parse(showDto.getTime());

	    // Check if the date is in the future or today
	    if (parsedDate.isBefore(LocalDate.now().plusDays(1))) {
	    	throw new ValidationException("Date should be future date");
	    }


	    Shows show = new Shows();
	    show.setDate(parsedDate);
	    show.setTime(parsedTime);

	    // Get movie
	    Movie movie = movieService.getMovieById(showDto.getMovieId());
	    show.setMovie(movie);

	    // Set seats and their relationship to the show
	    List<Seat> seats = showDto.getSeats();
	    for (Seat seat : seats) {
	        seat.setShow(show); // Set the show property for each seat
	    }
	    show.setSeats(seats);

	    // Associate show with theater
	    show.setTheater(theater);

	    // Save new show
	    return showRepository.save(show);
	}

	public Shows getShowById(int showId) {
		return showRepository.findById((long)showId).orElseThrow(() -> new ResourceNotFoundException("Show not found with ID: " + showId));
	}

	public List<Shows> getAllShowsByTheaterId(int theaterId) {
		Theater theater=theaterService.getTheaterById((long)theaterId);
		return theater.getShows();
	}

	public void deleteShowById(Long id) {
		//first delete booking related to show 
		List<Booking> getAllBookingRelatedToShowId=bookingRepository.findAllByShow_Id(id);
		
		for (Booking booking : getAllBookingRelatedToShowId) {
			bookingRepository.delete(booking);
		}
		
		showRepository.deleteById((long) id);
	}

	public Shows updateShow(ShowDto showDto, int id) {
		Shows show=getShowById(id);
		
		show.setDate(LocalDate.parse(showDto.getDate()));
		show.setTime(LocalTime.parse(showDto.getTime()));
		
		Movie movie=movieService.getMovieById(showDto.getMovieId());
		show.setMovie(movie);
		
		List<Seat> seats = showDto.getSeats();
	    for (Seat seat : seats) {
	        seat.setShow(show); // Set the show property for each seat
	    }
	    show.setSeats(seats);
		
		
		return showRepository.save(show);
		
	}

	public List<Seat> getAllSeats(int showId) {
		
		return getShowById(showId).getSeats();
	}

}
