package com.specdoctor.global.error.exception;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.specdoctor.global.error.ErrorCode;
import com.specdoctor.global.error.ErrorResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request,
		HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException, ServletException {
		setResponse(response);
	}

	public void setResponse(HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);

		ObjectMapper mapper = new ObjectMapper();
		String jsonResponse = mapper.writeValueAsString(
			ErrorResponse.of(ErrorCode.ROLE_FORBIDDEN));

		response.getWriter().write(jsonResponse);
	}
}
