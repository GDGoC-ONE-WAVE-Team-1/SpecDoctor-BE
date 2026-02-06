package com.specdoctor.global.auth.domain;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public record GoogleAccessToken(String accessToken) {

	public static GoogleAccessToken from(String jsonResponseBody) {
		JsonObject response = JsonParser.parseString(jsonResponseBody).getAsJsonObject();
		String accessToken = response.get("access_token").getAsString();

		return new GoogleAccessToken(accessToken);
	}
}
