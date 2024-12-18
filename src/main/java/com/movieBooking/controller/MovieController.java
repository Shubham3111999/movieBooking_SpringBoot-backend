package com.movieBooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieBooking.dto.MovieDto;
import com.movieBooking.dto.MovieLikedDto;
import com.movieBooking.exceptions.ValidationException;
import com.movieBooking.model.Movie;
import com.movieBooking.service.MovieService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/movie")
public class MovieController {
	
	@Autowired
	MovieService movieService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addMovie")
	public Movie addMovie(@Valid @RequestBody MovieDto movieDto, BindingResult result) {
		if(result.hasErrors()) {
			throw new ValidationException("Validation failed: " + result.getFieldErrors());
		}

		
		return movieService.addMovie(movieDto);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteMovie/{id}")
	public void deleteMovie(@PathVariable int id) {
		 movieService.deleteMovie(id);
	}
	
	
	@GetMapping("/getAllMovies")
	public List<Movie> getAllMovies() {
		return movieService.getAllMovies();
	}
	
	@GetMapping("/getMovieById/{movieId}")
	public Movie getMovieById(@PathVariable int movieId) {
		 return movieService.getMovieById(movieId);
	}
	
	@GetMapping("/getMovieByIdLiked/{movieId}/{userName}")
	public MovieLikedDto getMovieByIdLiked(@PathVariable int movieId ,@PathVariable String userName) {
		 return movieService.getMovieByIdLiked(movieId,userName);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateMovie/{id}")
	public Movie updateMovie(@RequestBody MovieDto movieDto,@PathVariable int id) {
		return movieService.updateMovie(movieDto,id);
	}
	
}
