package com.specdoctor.global.auth.usecase;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.specdoctor.domain.user.entity.User;
import com.specdoctor.domain.user.entity.enums.Role;
import com.specdoctor.domain.user.exception.UserNotFoundException;
import com.specdoctor.domain.user.repository.UserRepository;
import com.specdoctor.global.auth.domain.AuthDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public AuthDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
		return new AuthDetails(user.getId(), user.getEmail(), Role.USER);
	}
}
