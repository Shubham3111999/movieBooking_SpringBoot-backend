package com.movieBooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movieBooking.dto.BookingDtoResponse;
import com.movieBooking.dto.UserDto;
import com.movieBooking.exceptions.ValidationException;
import com.movieBooking.model.Booking;
import com.movieBooking.model.Movie;
import com.movieBooking.model.Shows;
import com.movieBooking.model.User;
import com.movieBooking.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/getUserById/{id}")
	public User getUserById(@PathVariable int id) {
		return userService.getUserById(id);
	}
	
	@PostMapping("/register")
	public User registerUser(@Valid @RequestBody UserDto userDto, BindingResult result) {
		
		if(result.hasErrors()) {
			throw new ValidationException("Validation failed: " + result.getFieldErrors());
		}
		
		return userService.registerUser(userDto);
	}
	
	@GetMapping("/getAllBookingForUser/{userName}")
	public List<BookingDtoResponse> getAllBookingForUser(@PathVariable String userName){
		return userService.getAllBookingForUser(userName);
	}
	
	
	@DeleteMapping("/deleteUser/{userId}")
	public void deleteUser(@PathVariable int userId) {
		userService.deleteUser(userId);
	}
	
	@PutMapping("/updateUser/{userId}")
	public User updateUser(@Valid @RequestBody UserDto userDto,@PathVariable int userId, BindingResult result) {
		
		if(result.hasErrors()) {
			throw new ValidationException("Validation failed: " + result.getFieldErrors());
		}
		
		return userService.updateUser(userDto,userId);
	}
	
	@GetMapping("/addLikedMovie/{userName}")
	public User addLikedMovieToUser(@PathVariable String userName, @RequestParam("movieId") int movieId ) {
		return userService.addLikedMovieToUser(userName,movieId);
	}
	
	@GetMapping("/removeLikedMovie/{userName}")
	public User removeLikedMovieToUser(@PathVariable String userName, @RequestParam("movieId") int movieId) {
		return userService.removeLikedMovieToUser(userName,movieId);
	}
	
	@GetMapping("/getAllLikedMovies/{userName}")
	public List<Movie> getAllLikedMovies(@PathVariable String userName){
		return userService.getAllLikedMovies(userName);
	}


}
