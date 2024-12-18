package com.movieBooking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
	
	@NotNull
	private String email;
	
	@NotNull
	private String password;
	
	@NotNull
	private RoleName role;

}




