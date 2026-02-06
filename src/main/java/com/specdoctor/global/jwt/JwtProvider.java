package com.specdoctor.global.jwt;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.specdoctor.domain.user.entity.enums.Role;
import com.specdoctor.global.auth.domain.AuthDetails;
import com.specdoctor.global.auth.usecase.AuthDetailsService;
import com.specdoctor.global.jwt.exception.ExpiredTokenException;
import com.specdoctor.global.jwt.exception.InvalidTokenException;
import com.specdoctor.global.jwt.exception.MalformedTokenException;
import com.specdoctor.global.jwt.exception.UnsupportedTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {

	public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(6);

	private final SecretKey secretKey;
	private final AuthDetailsService userAuthDetailsService;

	@Autowired
	public JwtProvider(@Value("${jwt.secret_key}") String secretKey,
		AuthDetailsService AuthDetailsService) {
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
		this.userAuthDetailsService = AuthDetailsService;
	}

	public String generateAccessToken(Long userId, String tokenSubject, Role role) {
		return generateToken(userId, tokenSubject, ACCESS_TOKEN_DURATION, role);
	}

	private String generateToken(Long userId, String tokenSubject, Duration duration, Role role) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + duration.toMillis());

		return Jwts.builder()
			.subject(tokenSubject)
			.claim("userId", userId)
			.claim("role", role.getRole())
			.expiration(expiry)
			.signWith(secretKey)
			.compact();
	}

	public Authentication getAuthentication(String token) {
		Claims claims = validateToken(token);
		Collection<? extends GrantedAuthority> authorities = Collections.singletonList(
			new SimpleGrantedAuthority(claims.get("role").toString()));

		return new UsernamePasswordAuthenticationToken(getDetails(claims), "", authorities);
	}

	private AuthDetails getDetails(Claims claims) {
		return this.userAuthDetailsService.loadUserByUsername(claims.getSubject());
	}

	private Claims parseClaims(String token) {
		return Jwts.parser()
			.verifyWith(secretKey).build()
			.parseSignedClaims(token)
			.getPayload();
	}

	public Claims validateToken(String token) {
		try {
			return parseClaims(token);
		} catch (IllegalArgumentException e) {
			throw new InvalidTokenException();
		} catch (ExpiredJwtException e) {
			throw new ExpiredTokenException();
		} catch (MalformedJwtException e) {
			throw new MalformedTokenException();
		} catch (UnsupportedJwtException e) {
			throw new UnsupportedTokenException();
		}
	}
}
