package com.movieBooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.movieBooking.dto.JwtRequest;
import com.movieBooking.dto.JwtResponse;
import com.movieBooking.jwt.JwtAuthenticationHelper;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthService {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtAuthenticationHelper jwtAuthenticationHelper;

	public JwtResponse login(JwtRequest jwtRequest) {
		doAuthenticate(jwtRequest.getEmail(),jwtRequest.getPassword());
		
		UserDetails userDetail=userDetailsService.loadUserByUsername(jwtRequest.getEmail());
		
		String token= jwtAuthenticationHelper.createJwtToken(userDetail);

		JwtResponse jwtResponse=JwtResponse.builder().jwtToken(token).build();
		
		return jwtResponse;
		
	}
	
	private void doAuthenticate(String username, String password) {
		UsernamePasswordAuthenticationToken createToken=new UsernamePasswordAuthenticationToken(username,password);
		
		
		try {
			authenticationManager.authenticate(createToken);  //authenticate
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Bad Credential");
		}
	}

}
