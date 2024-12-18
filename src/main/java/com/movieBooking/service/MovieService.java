package com.movieBooking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.movieBooking.dto.MovieDto;
import com.movieBooking.dto.MovieLikedDto;
import com.movieBooking.exceptions.ResourceNotFoundException;
import com.movieBooking.model.Location;
import com.movieBooking.model.Movie;
import com.movieBooking.model.Theater;
import com.movieBooking.model.User;
import com.movieBooking.repository.LocationRpository;
import com.movieBooking.repository.MovieRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MovieService {
	
	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	LocationRpository locationRpository;
	
	@Autowired
	UserService userService;
	

	public Movie addMovie(MovieDto movieDto) {
		
		Movie movie=new Movie();
		movie.setMovieName(movieDto.getMovieName());
		movie.setGenre(movieDto.getGenre());
		movie.setRating(movieDto.getRating());
		movie.setDuration(movieDto.getDuration());
		movie.setPosterUrl(movieDto.getPosterUrl());
		
		movie.setCast(movieDto.getCast());   //can be changes
		movie.setMovieDetail(movieDto.getMovieDetail());
		//movie.setLocations(movieDto.getLocations());
		
		List<Location> locations = new ArrayList<Location>();
		
		for (Location location : movieDto.getLocations()) {
	        // Query once and reuse the result
	        Optional<Location> existingLocation = locationRpository.findByName(location.getName());
	        
	        if (existingLocation.isPresent()) {
	            locations.add(existingLocation.get());
	        } else {
	            locations.add(location);  
	        }
	    }
		
		movie.setLocations(locations);
		
		return movieRepository.save(movie);
	}

	public void deleteMovie(long id) {
		 movieRepository.deleteById(id);
	}

	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}
	
	public Movie getMovieById(int id) {
		
		 return movieRepository.findById((long) id)
		            .orElseThrow(() -> new ResourceNotFoundException("Movie not found with ID: " + id));
	}

	public Movie updateMovie(MovieDto movieDto, int id) {   //need to code yet
		
		Movie movie=getMovieById(id);
		
		movie.setMovieName(movieDto.getMovieName());
		movie.setGenre(movieDto.getGenre());
		movie.setRating(movieDto.getRating());
		movie.setDuration(movieDto.getDuration());
		
		movie.setCast(movieDto.getCast());   //can be changes
		movie.setMovieDetail(movieDto.getMovieDetail());
		movie.setLocations(movieDto.getLocations());
		
		List<Location> locations = new ArrayList<Location>();
		
		for (Location location : movieDto.getLocations()) {
	        // Query once and reuse the result
	        Optional<Location> existingLocation = locationRpository.findByName(location.getName());
	        
	        if (existingLocation.isPresent()) {
	            locations.add(existingLocation.get());
	        } else {
	            locations.add(location);
	        }
	    }
		
		movie.setLocations(locations);
		
		return movieRepository.save(movie);
	}



	public MovieLikedDto getMovieByIdLiked(int movieId, String userName) {
		
		Movie movie=getMovieById(movieId);
		User user=userService.getUserByEmail(userName);
		
		boolean isLiked=false;
		
		for(Movie likedMovies:user.getLikedMovies()) {
			if(likedMovies.getId()==movieId) {
				isLiked=true;
				break;
			}
		}
		
		MovieLikedDto movieLikedDto=new MovieLikedDto();
		movieLikedDto.setId(movie.getId());
		movieLikedDto.setMovieName(movie.getMovieName());
		movieLikedDto.setPosterUrl(movie.getPosterUrl());
		movieLikedDto.setGenre(movie.getGenre());
		movieLikedDto.setCast(movie.getCast());
		movieLikedDto.setRating(movie.getRating());
		movieLikedDto.setDuration(movie.getDuration());
		movieLikedDto.setMovieDetail(movie.getMovieDetail());
		movieLikedDto.setLiked(isLiked);
		
		
		
		return movieLikedDto;
	}

	
	
}
