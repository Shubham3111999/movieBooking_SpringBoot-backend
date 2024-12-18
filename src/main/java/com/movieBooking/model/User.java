package com.movieBooking.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "user")
	@JsonBackReference
	private List<Booking> bookings;
	
	@ManyToMany
	@JoinTable(name="userId_LikedMovieId", joinColumns = @JoinColumn(name="userId"),inverseJoinColumns =  @JoinColumn(name="likedMovieId"))
	private List<Movie> likedMovies;
	
	@Column
	private String email;
	
	@Column
	private String password;
	
	
	@ManyToOne(cascade = CascadeType.PERSIST) 
	@JsonManagedReference
	private Role role;

	
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<SimpleGrantedAuthority> listToReturn=new ArrayList<SimpleGrantedAuthority>();
		listToReturn.add(new SimpleGrantedAuthority(role.getRoleName()));
		
		return listToReturn;
	}


	@Override
	public String getUsername() {
		return email;
	}
	
	

}
