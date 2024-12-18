package com.movieBooking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {
	
	private String jwtToken;
}
