package com.specdoctor.global.auth.domain;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

public record GoogleProfile(String email, String name) {

	public static GoogleProfile from(GoogleIdToken.Payload payload) {

		String email = payload.getEmail();
		String name = (String)payload.get("name");

		return new GoogleProfile(email, name);
	}
}

