package com.movieBooking.service;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.movieBooking.dto.BookingDtoResponse;
import com.movieBooking.dto.UserDto;
import com.movieBooking.exceptions.ResourceNotFoundException;
import com.movieBooking.exceptions.ValidationException;
import com.movieBooking.model.Booking;
import com.movieBooking.model.Movie;
import com.movieBooking.model.Role;
import com.movieBooking.model.Shows;
import com.movieBooking.model.User;
import com.movieBooking.repository.RoleRepository;
import com.movieBooking.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Transactional
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	MovieService movieService;

	public User getUserById(int userId) {
		return userRepository.findById((long) userId).orElseThrow(() -> new ResourceNotFoundException("user not found with ID: " + userId));
	}
	
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user not found with email: " + email));
	}

	public User registerUser(UserDto userDto) {
		User user=new User();
		
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		Role role=roleRepository.findByRoleName(userDto.getRole().name());
		
	
		user.setRole(role);
		return userRepository.save(user);
	}

	public List<BookingDtoResponse> getAllBookingForUser(String userName) {
		User user=userRepository.findByEmail(userName).get();
		
		List<BookingDtoResponse> res=new ArrayList<>();
		
		 for(Booking booking: user.getBookings()) {
			 BookingDtoResponse bookingRes=new BookingDtoResponse();
			 bookingRes.setId(booking.getId());
			 bookingRes.setTotalPrice(booking.getTotalPrice());
			 bookingRes.setBookingDateAndTime(booking.getBookingDateAndTime());
			 bookingRes.setShow(booking.getShow());
			 bookingRes.setSeats(booking.getSeats());
			 bookingRes.setUser(booking.getUser());
			 bookingRes.setTheater(booking.getShow().getTheater().getName());
			 bookingRes.setMovie(booking.getShow().getMovie().getMovieName());
			 
			 res.add(bookingRes);
		 }
		 
		 return res;
	}

	public void deleteUser(int userId) {
		userRepository.deleteById((long)userId);
	}

	public User updateUser(@Valid UserDto userDto, int userID) {
		User user=getUserById(userID);
		
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		Role role=roleRepository.findByRoleName(userDto.getRole().name());
		
	
		user.setRole(role);
		return userRepository.save(user);
	}

	public User addLikedMovieToUser(String userName, int movieId) {
		User user=getUserByEmail(userName);
		Movie movie=movieService.getMovieById(movieId);
		
		if(!user.getLikedMovies().contains(movie)) {
			user.getLikedMovies().add(movie);
		}
		
		return userRepository.save(user);
	}

	public User removeLikedMovieToUser(String userName, int movieId) {
		User user=getUserByEmail(userName);
		Movie movie=movieService.getMovieById(movieId);
		
		user.getLikedMovies().remove(movie);
		
		return userRepository.save(user);
	}

	public List<Movie> getAllLikedMovies(String userName) {
		User user=getUserByEmail(userName);
		return user.getLikedMovies();
	}
	

}
