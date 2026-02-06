package com.specdoctor.global.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.specdoctor.global.auth.handler.CustomOAuth2SuccessHandler;
import com.specdoctor.global.error.exception.CustomAccessDeniedHandler;
import com.specdoctor.global.filter.ExceptionHandleFilter;
import com.specdoctor.global.jwt.JwtAuthenticationEntryPoint;
import com.specdoctor.global.jwt.JwtProvider;
import com.specdoctor.global.jwt.TokenAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtProvider tokenProvider;
	private final JwtAuthenticationEntryPoint authenticationEntryPoint;
	private final CustomAccessDeniedHandler accessDeniedHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.httpBasic(AbstractHttpConfigurer::disable) // Basic 인증 방식 사용 안함
			.cors((cors) -> cors // CORS 설정
				.configurationSource(corsConfigurationSource())
			)
			.csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
			.formLogin(AbstractHttpConfigurer::disable) // Form 인증 방식 사용 안함
			.sessionManagement(
				(sessionManagement) -> sessionManagement.sessionCreationPolicy(
					SessionCreationPolicy.ALWAYS)) // 세션 항상 생성
			.oauth2Login(oauth2 -> oauth2.successHandler(new CustomOAuth2SuccessHandler())); // OAuth2 로그인

		http
			.authorizeHttpRequests((authorize) ->
				authorize
					.requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll() // API 명세서
					.requestMatchers("/login/**").permitAll() // 구글 로그인
					.requestMatchers("/v1/user/**").permitAll() // 사용자
					.anyRequest().permitAll()
			);

		http
			.exceptionHandling(exceptionHandlingCustomizer ->
				exceptionHandlingCustomizer
					.authenticationEntryPoint(authenticationEntryPoint)
					.accessDeniedHandler(accessDeniedHandler)
			);

		http
			.addFilterBefore(new TokenAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new ExceptionHandleFilter(), TokenAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList(
			"http://localhost:8080",
			"http://localhost:3000"));
		configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(List.of("*"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
