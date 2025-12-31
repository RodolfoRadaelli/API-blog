package com.practice.blog.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Date;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import javax.crypto.SecretKey;
import java.util.Base64;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String SECRETCODE;
	@Value("${jwt.expiration}")
	private long VALIDITY;

	public String createToken(UserDetails userDetails) {
		return Jwts.builder()
				.subject(userDetails.getUsername())
				.issuedAt(Date.from(Instant.now()))
				.expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
				.signWith(generateKey())
				.compact();
	}

	public SecretKey generateKey() {
		byte[] decodedKey = Base64.getDecoder().decode(SECRETCODE);
		return Keys.hmacShaKeyFor(decodedKey);
	}

	public String extractUsername(String jwt) {
		Claims claims = getClaims(jwt);
		return claims.getSubject();
	}

	private Claims getClaims(String jwt) {
		return Jwts.parser()
				.verifyWith(generateKey())
				.build()
				.parseSignedClaims(jwt)
				.getPayload();
	}

	public boolean isTokenValid(String jwt) {
		Claims claims = getClaims(jwt);
		return claims.getExpiration().after(Date.from(Instant.now()));
	}

}
