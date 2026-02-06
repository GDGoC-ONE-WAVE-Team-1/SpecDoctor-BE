package com.specdoctor.global.auth.usecase;

import static com.specdoctor.global.error.ErrorCode.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.specdoctor.domain.user.entity.User;
import com.specdoctor.domain.user.entity.enums.Role;
import com.specdoctor.domain.user.repository.UserRepository;
import com.specdoctor.global.auth.domain.GoogleProfile;
import com.specdoctor.global.auth.presentation.dto.response.AuthResponse;
import com.specdoctor.global.error.exception.ServiceException;
import com.specdoctor.global.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final JwtProvider jwtProvider;

	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String clientId;

	@Transactional
	public AuthResponse getUserInfo(String token) {
		return getUserInfoFromToken(token); // AccessToken을 이용하여 사용자 정보 조회
	}

	// AccessToken을 이용하여 사용자 정보 조회
	private AuthResponse getUserInfoFromToken(String token) {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
			.setAudience(Collections.singletonList(clientId))
			.build();

		GoogleIdToken idToken;

		try {
			idToken = verifier.verify(token);
		} catch (GeneralSecurityException | IOException e) {
			throw new ServiceException(FAIL_GOOGLE_OAUTH);
		}

		if (idToken != null) {
			// 유저 정보 추출
			GoogleIdToken.Payload payload = idToken.getPayload();

			// 사용자 정보 조회 후 User 객체 생성
			User user = getUser(GoogleProfile.from(payload));

			// AccessToken, RefreshToken 생성 후 반환
			return createToken(user);
		} else {
			throw new ServiceException(BAD_GOOGLE_TOKEN);
		}
	}

	private User getUser(GoogleProfile googleProfile) {
		String email = googleProfile.email(); // 사용자 이메일
		String name = googleProfile.name(); // 사용자 이름

		// 사용자 정보가 없으면 User 객체 생성 후 저장 (회원 가입)
		return userRepository.findByEmail(email)
			.orElseGet(() -> userRepository.save(User.create(name, email, Role.USER)));
	}

	private AuthResponse createToken(User user) {
		String accessToken = jwtProvider.generateAccessToken(user.getId(), user.getName(), user.getRole());

		userRepository.save(user);

		return new AuthResponse(accessToken, user.getName());
	}
}
