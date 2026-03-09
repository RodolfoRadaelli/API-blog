package com.practice.blog.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Date;
import java.time.Instant;
import javax.crypto.SecretKey;
import java.util.Base64;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String SECRETCODE;
	@Value("${jwt.expiration}")
	private long VALIDITY;

	public String createToken(UserDetails userDetails) {
		String role = userDetails.getAuthorities()
				.stream()
				.findFirst()
				.map(GrantedAuthority::getAuthority)
				.orElse("ROLE_USER");

		return Jwts.builder()
				.subject(userDetails.getUsername())
				.claim("role", role)
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

	public String extractRole(String jwt) {
		Claims claims = getClaims(jwt);
		return claims.get("role", String.class);
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
