package com.movieBooking.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.movieBooking.exceptions.CustomTokenExpiredException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtAuthenticationHelper {
	
	private String SECRET_KEY = "nsjndsnjdsnkjdbkbcabcshbacsjbcjbhjbasckjbcasbajbacskjbcasbhjacbjabacjbckajbjacsbjhacsbjacsbjcabjabjacbjcasbjhabcahbacshjacbjbusss6sts6s7ssya";

	public String getUserNameByToken(String Token) {
		Claims claim = getClaimByToken(Token);

		return claim.getSubject(); // get username from body of token
	}

	public Claims getClaimByToken(String Token) {
		Claims claim=null;
		try {
			 claim = Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(Token)
					.getBody();
		} catch (Exception e) {
			throw new CustomTokenExpiredException("token expired");
		}
		return claim;
	}

	public Boolean isTokenExpired(String Token) {
		Claims claim = getClaimByToken(Token);

		Date expireDate = claim.getExpiration();
		
		return expireDate.before(new Date()); // check if token is not expired
	}

	// create JWT token
	public String createJwtToken(UserDetails userDetail) {

		Map<String, Object> claims = new HashMap<>(); // initialize claim with hash

		return Jwts.builder().setClaims(claims) // set claim
				.setSubject(userDetail.getUsername()) // set username
				.setIssuedAt(new Date(System.currentTimeMillis())) // set date
				.setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
				.signWith(new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS512.getJcaName()),
						SignatureAlgorithm.HS512)
				.compact();

	}

}
