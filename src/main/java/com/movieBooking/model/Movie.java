package com.movieBooking.model;

import java.util.HashMap;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String movieName;
	
	@Column
	private String posterUrl;
	
	@Column
	private String genre;
	
	@Column
	private List<String> cast;
	
	@Column
	private Integer rating;
	
	@Column
	private String duration;
	
	@Column
	private String movieDetail;
	
	
	
	@ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(joinColumns = @JoinColumn(name = "Movie_Id"),inverseJoinColumns = @JoinColumn(name="Location_Id"), name = "Movie_Location")
	private List<Location> locations;
	
}
