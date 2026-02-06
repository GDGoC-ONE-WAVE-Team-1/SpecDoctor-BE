package com.specdoctor.global.auth.domain;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public record GoogleProfile(String email, String name) {

	public static GoogleProfile from(String jsonResponseBody) {
		JsonObject object = JsonParser.parseString(jsonResponseBody).getAsJsonObject();

		String email = object.get("email").getAsString();
		String name = object.get("name").getAsString();

		return new GoogleProfile(email, name);
	}
}

