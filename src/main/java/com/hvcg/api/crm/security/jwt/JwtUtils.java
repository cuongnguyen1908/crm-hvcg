package com.hvcg.api.crm.security.jwt;

import com.hvcg.api.crm.security.service.UserDetailImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class JwtUtils {


	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	private static String secretKey;

	public JwtUtils(@Value("${application.jwt.secretKey}") String secretKey) {
		this.secretKey = secretKey;
	}


//	@Value("${application.jwt.secretKey}")
//	public static String jwtSecret;

	@Value("${application.jwt.tokenExpirationAfterDays}")
	private int jwtExpirationDay;

	@Value("${application.jwt.tokenPrefix}")
	private String jwtPreFix;

	public String generateJwtToken(Authentication authentication) {

		UserDetailImpl userPrincipal = (UserDetailImpl) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.claim("authorities", userPrincipal.getAuthorities())
				.setIssuedAt(new Date())
				.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtExpirationDay)))
				.signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
				.compact();
	}

	public static String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {

			Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
					.parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}
