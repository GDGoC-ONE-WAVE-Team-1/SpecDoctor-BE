package com.specdoctor.global.auth.usecase;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.specdoctor.domain.user.entity.User;
import com.specdoctor.domain.user.entity.enums.Role;
import com.specdoctor.domain.user.repository.UserRepository;
import com.specdoctor.global.auth.domain.GoogleAccessToken;
import com.specdoctor.global.auth.domain.GoogleProfile;
import com.specdoctor.global.auth.presentation.dto.response.AuthResponse;
import com.specdoctor.global.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private static final String TOKEN_URI = "https://oauth2.googleapis.com/token";
	private static final String USER_INFO_URI = "https://www.googleapis.com/oauth2/v2/userinfo";

	private final UserRepository userRepository;
	private final JwtProvider jwtProvider;

	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String clientId;

	@Value("${spring.security.oauth2.client.registration.google.client-secret}")
	private String clientSecret;

	@Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
	private String redirectUri;

	@Transactional
	public AuthResponse getUserInfo(String code) {
		String token = getToken(code); // Google OAuth2.0 인증 코드를 이용하여 AccessToken 발급
		return getUserInfoFromToken(token); // AccessToken을 이용하여 사용자 정보 조회
	}

	// Google OAuth2.0 인증 코드를 이용하여 AccessToken 발급
	private String getToken(String code) {
		RestTemplate restTemplate = new RestTemplate(); // RestTemplate 객체 생성

		HttpHeaders headers = new HttpHeaders(); // HttpHeader 객체 생성
		String decode = URLDecoder.decode(code, StandardCharsets.UTF_8); //	code 디코딩
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // Content-type 설정

		// AccessToken 발급을 위한 요청 Body 생성
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", clientId);
		body.add("redirect_uri", redirectUri);
		body.add("code", decode);
		body.add("client_secret", clientSecret);
		HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(body, headers);

		// AccessToken 발급 요청
		ResponseEntity<String> response = restTemplate.postForEntity(TOKEN_URI, tokenRequest, String.class);

		// AccessToken 반환
		return GoogleAccessToken.from(response.getBody()).accessToken();
	}

	// AccessToken을 이용하여 사용자 정보 조회
	private AuthResponse getUserInfoFromToken(String token) {
		RestTemplate restTemplate = new RestTemplate(); // RestTemplate 객체 생성

		// 요청 Header 설정
		HttpHeaders headers = new HttpHeaders(); // HttpHeader 객체 생성
		headers.add("Authorization", "Bearer " + token); // 헤더에 AccessToken 추가
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // Content-type 설정
		HttpEntity<Void> profileRequest = new HttpEntity<>(headers);

		// 사용자 정보 조회 요청
		ResponseEntity<String> response = restTemplate.exchange(USER_INFO_URI, HttpMethod.GET, profileRequest,
			String.class);

		// 사용자 정보 조회 후 User 객체 생성
		User user = getUser(GoogleProfile.from(response.getBody()));

		// AccessToken, RefreshToken 생성 후 반환
		return createToken(user);
	}

	private User getUser(GoogleProfile googleProfile) {
		String email = googleProfile.email(); // 사용자 이메일
		String name = googleProfile.name(); // 사용자 이름

		// 사용자 정보가 없으면 User 객체 생성 후 저장 (회원 가입)
		return userRepository.findByEmail(email)
			.orElseGet(() -> userRepository.save(User.create(email, name, Role.USER)));
	}

	private AuthResponse createToken(User user) {
		String accessToken = jwtProvider.generateAccessToken(user.getId(), user.getEmail(), user.getRole());

		userRepository.save(user);

		return new AuthResponse(accessToken, user.getName());
	}
}
